package lamph11.web.centrerapi.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
@Table(
        name = "lph_social_account",
        uniqueConstraints = {
                @UniqueConstraint(name = "SocialAccount_UNIQUE1", columnNames = {"provider", "provider_id"})
        }
)
public class SocialAccount {

    @Id
    @Column(length = 100)
    private String id;

    @NotEmpty
    @Length(max = 50)
    @Column(name = "provider", length = 50, nullable = false)
    private String oauth2Provider;

    @NotEmpty
    @Length(max = 100)
    @Column(name = "provider_id", length = 100, nullable = false)
    private String providerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo userInfo;
}
