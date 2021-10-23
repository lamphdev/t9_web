package lamph11.web.centrerapi.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import lamph11.web.centrerapi.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, String>, EntityGraphJpaSpecificationExecutor<Menu> {
}
