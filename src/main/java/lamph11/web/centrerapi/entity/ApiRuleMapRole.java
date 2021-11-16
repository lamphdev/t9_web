package lamph11.web.centrerapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lph_api_rule_map_role", uniqueConstraints = {
        @UniqueConstraint(
                name = "API_RULE_MAP_ROLE_UNIQUE",
                columnNames = {"api_rule_id", "role_id"}
        )
})
public class ApiRuleMapRole {

    @Id
    @Column(length = 100)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "api_rule_id", nullable = false)
    private ApiRule apiRule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
