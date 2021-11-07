package lamph11.web.centrerapi.resources;

import lamph11.web.centrerapi.common.auth.TokenProvider;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.common.io.CookieUtils;
import lamph11.web.centrerapi.entity.UserInfo;
import lamph11.web.centrerapi.resources.dto.auth.AuthResponse;
import lamph11.web.centrerapi.resources.dto.auth.UserInfoDTO;
import lamph11.web.centrerapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthService authService;
    private final TokenProvider tokenProvider;
    private final CookieUtils cookieUtils;

    @RequestMapping("/user-info")
    public ResponseEntity getUserInfo(HttpServletRequest httpRequest) throws LphException {
        UserInfo userInfo = authService.getUserInfo(httpRequest);
        UserInfoDTO userInfoDTO = UserInfoDTO.fromUserInfo(userInfo);
        return ResponseEntity.ok(userInfoDTO);
    }

    @PostMapping("/social/{provider}")
    public ResponseEntity socialLogin(
            @NotNull @PathVariable String provider, @NotNull @RequestParam String token, @RequestParam Boolean writeCookie
    ) throws LphException {
        UserInfo userInfo = authService.socialLogin(provider, token);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userInfo.getId(),
                null,
                Collections.emptyList()
        );
        String generatedToken = tokenProvider.generateToken(authentication);
        if (writeCookie) {
            cookieUtils.writeCookie(AuthService.TOKEN_COOKIE_NAME, generatedToken, 6 * 60 * 60 * 1000);
        }

        return ResponseEntity.ok(
                new AuthResponse(
                        UserInfoDTO.fromUserInfo(userInfo),
                        generatedToken
                )
        );
    }

}
