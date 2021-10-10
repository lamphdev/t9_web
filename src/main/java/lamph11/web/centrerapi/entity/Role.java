package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
