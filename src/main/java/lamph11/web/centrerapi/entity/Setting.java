package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Entity
@Table(name = "lph_setting")
public class Setting {

    @Id
    @Length(max = 200)
    @Column(length = 200)
    private String code;

    private String description;

    @Column(length = 1024)
    private String metadata;

    @OneToMany(mappedBy = "setting")
    private List<SettingOption> options;

    @PrePersist
    public void upperCode() {
        this.code = StringUtils.upperCase(code);
    }
}
