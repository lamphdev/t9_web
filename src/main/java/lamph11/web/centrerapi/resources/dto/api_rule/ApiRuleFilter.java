package lamph11.web.centrerapi.resources.dto.api_rule;

import io.github.jhipster.service.filter.StringFilter;
import lombok.Data;

@Data
public class ApiRuleFilter {

    private StringFilter id;
    private StringFilter urlPattern;
    private StringFilter method;
}
