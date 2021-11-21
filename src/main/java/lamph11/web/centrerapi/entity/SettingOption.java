package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "lph_setting_option", uniqueConstraints = {
        @UniqueConstraint(name = "OPTION_CAN_NOT_DUPLICATE", columnNames = {"setting", "value"})
})
public class SettingOption {

    @Id
    @Column(length = 100)
    private String id;

    @NotEmpty
    @Length(max = 250)
    @Column(nullable = false, length = 250)
    private String name;

    @NotEmpty
    @Length(max = 500)
    @Column(nullable = false, length = 500)
    private String value;

    @Length(max = 1024)
    @Column(length = 1024)
    private String metadata;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setting", nullable = false)
    private Setting setting;
}
