package lamph11.web.centrerapi.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import lamph11.web.centrerapi.entity.ApiRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiRuleRepository extends JpaRepository<ApiRule, String>,
        EntityGraphJpaSpecificationExecutor<ApiRule> {
}
