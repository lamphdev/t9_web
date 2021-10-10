package lamph11.web.centrerapi.common;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setPropertyCondition(mappingContext -> {
            if (!(mappingContext.getSource() instanceof HibernateProxy))
                return true;
            LazyInitializer lazyInitializer = ((HibernateProxy) mappingContext.getSource()).getHibernateLazyInitializer();
            return lazyInitializer.isUninitialized();
        });
        return modelMapper;
    }
}
