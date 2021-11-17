package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lph_role")
public class Role {

    @Id
    @Length(max = 100)
    @Column(length = 100)
    private String role;

    @Length(max = 1024)
    @Column(length = 1024)
    private String description;

    private String metadata;

    @PrePersist
    public void upperCaseRoleName() {
        this.role = StringUtils.toRootUpperCase(role);
    }
}
