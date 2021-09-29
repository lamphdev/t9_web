package lamph11.web.centrerapi.resources;

import com.nimbusds.oauth2.sdk.ParseException;
import lamph11.web.centrerapi.common.auth.TokenProvider;
import lamph11.web.centrerapi.common.exception.LphException;
import lamph11.web.centrerapi.config.ExceptionContains;
import lamph11.web.centrerapi.entity.UserInfo;
import lamph11.web.centrerapi.resources.dto.auth.UserInfoDTO;
import lamph11.web.centrerapi.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthService authService;
    private final TokenProvider tokenProvider;

    @RequestMapping("/user-info")
    public ResponseEntity getUserInfo(HttpServletRequest httpRequest) throws LphException {
        UserInfo userInfo = authService.getUserInfo(httpRequest);
        UserInfoDTO userInfoDTO = UserInfoDTO.fromUserInfo(userInfo);
        return ResponseEntity.ok(userInfoDTO);
    }

    @PostMapping("/{provider}")
    public ResponseEntity socialLogin(
            @NotNull @PathVariable String provider, @NotNull @RequestHeader String token
    ) throws LphException, IOException, ParseException {
        authService.socialLogin(provider, token);
        return ResponseEntity.ok(true);
    }

}
