package lamph11.web.centrerapi.resources.dto.setting;

import lamph11.web.centrerapi.common.validate.ValidateMessage;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionDTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class SettingDTO {

    @NotEmpty(message = ValidateMessage.REQUIRED)
    private String code;

    @Length(max = 1024, message = ValidateMessage.MAX_LENGTH)
    private String description;

    @Length(max = 500, message = ValidateMessage.MAX_LENGTH)
    private String metadata;

    private List<SettingOptionDTO> options;
}
