package lamph11.web.centrerapi.repository;

import lamph11.web.centrerapi.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
}
