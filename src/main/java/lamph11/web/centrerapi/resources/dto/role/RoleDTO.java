package lamph11.web.centrerapi.resources.dto.role;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class RoleDTO {

    @NotEmpty
    @Length(max = 100)
    private String role;

    @Length(max = 1024)
    private String description;

    @Length(max = 1024)
    private String metadata;
}
