package lamph11.web.centrerapi;

import com.cosium.spring.data.jpa.entity.graph.repository.support.EntityGraphJpaRepositoryFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.StringUtils;

@SpringBootApplication
@EnableJpaRepositories(
        repositoryFactoryBeanClass = EntityGraphJpaRepositoryFactoryBean.class
)
public class CentraApiApplication {

    public static void main(String[] args) {
        String profilesString = System.getenv("LPH_ACTIVE_PROFILE");
        if (!StringUtils.hasText(profilesString))
            profilesString = "dev";

        String[] activeProfiles = profilesString.split(",");
        ConfigurableEnvironment environment = new StandardEnvironment();
        environment.setActiveProfiles(activeProfiles);

        SpringApplication application = new SpringApplication(CentraApiApplication.class);
        application.setEnvironment(environment);
        application.run(args);
    }

}
