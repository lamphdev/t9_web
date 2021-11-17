package lamph11.web.centrerapi.resources.dto.api_rule;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class ApiRuleDTO {

    private String id;

    private String urlPattern;

    @NotEmpty
    @Length(max = 10)
    private String method;

    @Length(max = 1024)
    private String description;

    private List<String> roles;

}