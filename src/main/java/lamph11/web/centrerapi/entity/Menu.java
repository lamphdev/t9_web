package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "lph_menu")
public class Menu {

    @Id
    private String id;

    @NotEmpty
    @Length(max = 200)
    @Column(name = "display_text", length = 200)
    private String displayText;

    @Length(max = 200)
    @Column(length = 500)
    private String action;

    @Length(max = 200)
    @Column(length = 200)
    private String icon;

    @Length(max = 1024)
    @Column(length = 1024)
    private String path;

    @OneToMany(mappedBy = "menu")
    private List<MenuMapRole> mapRoles;


}
