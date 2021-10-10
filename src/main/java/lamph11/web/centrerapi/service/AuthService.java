package lamph11.web.centrerapi.service;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.nimbusds.openid.connect.sdk.UserInfoSuccessResponse;
import lamph11.web.centrerapi.common.auth.Oauth2Provider;
import lamph11.web.centrerapi.common.auth.TokenProvider;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.exception.ResourceNotFoundException;
import lamph11.web.centrerapi.common.utils.UUIDUtils;
import lamph11.web.centrerapi.config.ExceptionContains;
import lamph11.web.centrerapi.entity.SocialAccount;
import lamph11.web.centrerapi.entity.UserInfo;
import lamph11.web.centrerapi.repository.SocialAccountRepository;
import lamph11.web.centrerapi.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class AuthService {

    public static final String TOKEN_HEADER_NAME = "token";
    public static final String TOKEN_PARAM_NAME = "token";

    private final TokenProvider tokenProvider;
    private final UserInfoRepository userInfoRepository;
    private final SocialAccountRepository socialAccountRepository;


    /**
     * get user info
     *
     * @param httpRequest
     * @return user info
     * @throws LphException validate, runtime exception
     */
    public UserInfo getUserInfo(HttpServletRequest httpRequest) throws LphException {
        String token = Optional.ofNullable(httpRequest.getHeader(TOKEN_HEADER_NAME))
                .orElse((String) httpRequest.getAttribute(TOKEN_PARAM_NAME));

        if (StringUtils.isEmpty(token))
            throw new LphException(ExceptionContains.AUTH_TOKEN_IS_REQUIRED);

        Authentication authentication = tokenProvider.parseToken(token);
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findById(authentication.getName());
        if (!optionalUserInfo.isPresent())
            throw new ResourceNotFoundException(null);
        return optionalUserInfo.get();
    }

    /**
     * handle user login by social account (ex: google, facebook ...)
     *
     * @param provider oauth2 provider (gg = google, fb = facebook, ...)
     * @param token    token provided by oauth2 provider
     * @throws LphException validate, runtime exception
     */
    public UserInfo socialLogin(String provider, String token) throws LphException {
        SocialAccount socialAccount = null;
        try {
            Optional<Oauth2Provider> optionalProvider = Oauth2Provider.fromName(provider);
            if (!optionalProvider.isPresent())
                throw new LphException(ExceptionContains.AUTH_PROVIDER_NOT_SUPPORT);


            Oauth2Provider oauth2Provider = optionalProvider.get();
            UserInfoRequest userInfoRequest = new UserInfoRequest(
                    URI.create(oauth2Provider.getUserInfoEndpoint()), new BearerAccessToken(token)
            );
            HTTPResponse response = userInfoRequest.toHTTPRequest().send();


            if (response.indicatesSuccess()) {
                UserInfoSuccessResponse userInfoResponse = UserInfoSuccessResponse.parse(response);
                Optional<SocialAccount> optionalSocialAccount = socialAccountRepository.findSocialAccount(
                        provider, String.valueOf(userInfoResponse.getUserInfo().getSubject())
                );
                if (optionalSocialAccount.isPresent()) {
                    socialAccount = optionalSocialAccount.get();
                } else {
                    socialAccount = registerNewUserFromSocial(oauth2Provider, userInfoResponse.getUserInfo());
                }
                return socialAccount.getUserInfo();
            } else {
                // UserInfoErrorResponse userInfoErrorResponse = UserInfoErrorResponse.parse(response);
                throw new LphException(ExceptionContains.AUTH_READ_USER_INFO_RESPONSE_ERROR);
            }
        } catch (IOException e) {
            throw new LphException(ExceptionContains.AUTH_GET_USER_INFO_ERROR);
        } catch (ParseException e) {
            throw new LphException(ExceptionContains.AUTH_READ_USER_INFO_RESPONSE_ERROR);
        }
    }


    public SocialAccount registerNewUserFromSocial(
            Oauth2Provider oauth2Provider, com.nimbusds.openid.connect.sdk.claims.UserInfo socialUserInfo
    ) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(UUIDUtils.generateId());
        userInfo.setFullName(socialUserInfo.getGivenName());
        userInfo.setEmail(socialUserInfo.getEmailAddress());
        userInfo.setImage(socialUserInfo.getPicture().toString());
        userInfo.setPhone(socialUserInfo.getPhoneNumber());
        userInfoRepository.save(userInfo);

        SocialAccount socialAccount = new SocialAccount();
        socialAccount.setId(UUIDUtils.generateId());
        socialAccount.setOauth2Provider(oauth2Provider.getName());
        socialAccount.setProviderId(String.valueOf(socialUserInfo.getSubject()));
        socialAccount.setUserInfo(userInfo);
        socialAccountRepository.save(socialAccount);

        return socialAccount;
    }
}
