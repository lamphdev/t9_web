package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "lph_setting", uniqueConstraints = {
        @UniqueConstraint(name = "SETTING_NAME_IS_REQUIRED", columnNames = {"name"})
})
public class Setting {

    @Id
    @Column(length = 100)
    private String id;

    @NotEmpty
    @Length(max = 250)
    @Column(nullable = false, length = 250)
    private String name;

    @Column(length = 1024)
    private String metadata;

    @OneToMany(mappedBy = "setting")
    private List<SettingOption> options;
}
