package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(name = "lph_account")
public class Account {

    @Id
    @Column(length = 100)
    private String id;

    @NotEmpty
    @Length(max = 200)
    @Column(length = 200, unique = true)
    private String username;


    @NotEmpty
    @Length(max = 100)
    @Column(length = 100, unique = true)
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userInfo;
}
