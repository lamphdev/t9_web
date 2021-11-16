package lamph11.web.centrerapi.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import lamph11.web.centrerapi.entity.ApiRuleMapRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRuleMapRoleRepository extends JpaRepository<ApiRuleMapRole, String>,
        EntityGraphJpaSpecificationExecutor<ApiRuleMapRole> {
}
