package lamph11.web.centrerapi.resources.dto.setting_option;

import lamph11.web.centrerapi.common.validate.ValidateMessage;
import lamph11.web.centrerapi.resources.dto.setting.SettingDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class SettingOptionDTO {

    private String id;

    @NotEmpty(message = ValidateMessage.REQUIRED)
    @Length(max = 100)
    private String name;

    @NotEmpty(message = ValidateMessage.REQUIRED)
    @Length(max = 250, message = ValidateMessage.MAX_LENGTH)
    private String value;

    @Length(max = 500, message = ValidateMessage.MAX_LENGTH)
    private String metadata;

    private SettingDTO setting;
}
