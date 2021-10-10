package lamph11.web.centrerapi.resources.dto.setting;

import lamph11.web.centrerapi.entity.SettingOption;
import lamph11.web.centrerapi.resources.dto.setting_option.SettingOptionDTO;
import lombok.Data;

import java.util.List;

@Data
public class SettingDTO {

    private String id;

    private String name;

    private String metadata;

    private List<SettingOptionDTO> options;
}
