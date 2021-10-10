package lamph11.web.centrerapi.resources.dto.setting_option;

import lamph11.web.centrerapi.entity.Setting;
import lombok.Data;

@Data
public class SettingOptionDTO {

    private String id;

    private String name;

    private String value;

    private String metadata;

    private Setting setting;
}
