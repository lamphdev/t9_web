package lamph11.web.centrerapi.resources.dto.setting;

import io.github.jhipster.service.filter.StringFilter;
import lamph11.web.centrerapi.common.io.PageableRequest;
import lombok.Data;

@Data
public class SettingFilter extends PageableRequest {

    private StringFilter code;

    private String keyword;

}
