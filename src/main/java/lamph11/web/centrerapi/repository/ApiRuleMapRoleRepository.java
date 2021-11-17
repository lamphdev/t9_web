package lamph11.web.centrerapi.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import lamph11.web.centrerapi.entity.ApiRuleMapRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiRuleMapRoleRepository extends JpaRepository<ApiRuleMapRole, String>,
        EntityGraphJpaSpecificationExecutor<ApiRuleMapRole> {

    @EntityGraph(attributePaths = "role")
    @Query("select m from ApiRuleMapRole m where m.role.role = ?1")
    List<ApiRuleMapRole> findByRole(String role);
}
