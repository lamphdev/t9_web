package lamph11.web.centrerapi.resources.dto.auth;

import lamph11.web.centrerapi.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {

    private String id;
    private String fullName;
    private Integer age;
    private String email;
    private String image;
    private String phone;

    public static UserInfoDTO fromUserInfo(UserInfo userInfo) {
        return UserInfoDTO.builder()
                .id(userInfo.getId())
                .fullName(userInfo.getFullName())
                .age(userInfo.getAge())
                .email(userInfo.getEmail())
                .image(userInfo.getImage())
                .phone(userInfo.getPhone())
                .build();
    }
}
