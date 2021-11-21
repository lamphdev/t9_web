package lamph11.web.centrerapi.resources.dto.setting_option;

import lamph11.web.centrerapi.common.io.PageableRequest;
import lombok.Data;

@Data
public class SettingOptionFilter extends PageableRequest {

    private String keyword;

    private String setting;

}
