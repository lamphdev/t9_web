package lamph11.web.centrerapi.repository;

import com.cosium.spring.data.jpa.entity.graph.repository.EntityGraphJpaSpecificationExecutor;
import lamph11.web.centrerapi.entity.SettingOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingOptionRepository extends JpaRepository<SettingOption, String>,
        EntityGraphJpaSpecificationExecutor<SettingOption> {
}
