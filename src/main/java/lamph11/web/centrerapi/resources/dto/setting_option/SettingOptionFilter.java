package lamph11.web.centrerapi.resources.dto.setting_option;

import io.github.jhipster.service.filter.StringFilter;
import lamph11.web.centrerapi.common.io.PageableRequest;
import lamph11.web.centrerapi.resources.dto.setting.SettingFilter;
import lombok.Data;

@Data
public class SettingOptionFilter extends PageableRequest {

    private StringFilter id;

    private StringFilter name;

    private StringFilter value;

    private StringFilter metadata;

    private SettingFilter setting;
}
