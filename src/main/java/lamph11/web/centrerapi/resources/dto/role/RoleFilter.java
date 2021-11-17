package lamph11.web.centrerapi.resources.dto.role;

import lamph11.web.centrerapi.common.io.PageableRequest;
import lombok.Data;

@Data
public class RoleFilter extends PageableRequest {

    private String name;
    private String keyword;
}
