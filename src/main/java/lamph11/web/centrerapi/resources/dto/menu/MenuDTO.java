package lamph11.web.centrerapi.resources.dto.menu;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MenuDTO {

    @EqualsAndHashCode.Include
    private String id;

    @NotEmpty
    @Length(max = 200)
    private String displayText;

    @Length(max = 500)
    private String action;

    @Length(max = 200)
    private String icon;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String path;

    private List<MenuDTO> menu = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String parentId;

    public MenuDTO(String id) {
        this.id = id;
    }

}
