package lamph11.web.centrerapi.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "lph_api_rule", uniqueConstraints = {
        @UniqueConstraint(
                name = "API_RULE_UNIQUE1",
                columnNames = {"url_pattern", "method"}
        )
})
public class ApiRule {

    @Id
    @Column(length = 100)
    private String id;

    @Column(name = "url_pattern", length = 250)
    private String urlPattern;

    @Column(length = 10)
    private String method;

    @Column(length = 1024)
    private String description;

    @OneToMany(mappedBy = "apiRule")
    private List<ApiRuleMapRole> mapRoles;
}
