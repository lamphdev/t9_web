package lamph11.web.centrerapi.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "menu_map_role", uniqueConstraints = {
        @UniqueConstraint(name = "UNIQUE_MENU_AND_ROLE", columnNames = {"menu_id", "role_id"})
})
public class MenuMapRole {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
