package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lph_user_map_role", uniqueConstraints = {
        @UniqueConstraint(name = "USER_MAP_ROLE_UNIQUE", columnNames = {"user_id", "role"})
})
public class UserMapRole {

    @Id
    @Length(max = 100)
    @Column(length = 100)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role", nullable = false)
    private Role role;

}
