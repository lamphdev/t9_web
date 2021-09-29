package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "lph_user_info")
public class UserInfo {

    @Id
    @Column(length = 100)
    private String id;

    @Length(max = 200)
    @Column(length = 200)
    private String fullName;

    @Range(min = 1, max = 200)
    private Integer age;

    @Length(max = 250)
    @Column(length = 250)
    private String email;

    @Length(max = 250)
    @Column(length = 250)
    private String image;

    @Length(max = 20)
    @Column(length = 20)
    private String phone;
}
