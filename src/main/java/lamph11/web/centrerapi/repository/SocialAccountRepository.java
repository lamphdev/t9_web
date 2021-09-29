package lamph11.web.centrerapi.repository;

import lamph11.web.centrerapi.entity.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SocialAccountRepository extends JpaRepository<SocialAccount, String> {


    @Query("select sc from SocialAccount sc where sc.oauth2Provider like ?1 and sc.providerId like ?2")
    Optional<SocialAccount> findSocialAccount(String oauth2Provider, String idByProvider);
}
