package lamph11.web.centrerapi.resources.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private UserInfoDTO userInfo;
    private String token;
}
