package lamph11.web.centrerapi.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class Beans {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.getConfiguration().setPropertyCondition(mappingContext -> {
            if (mappingContext.getSource() instanceof PersistentCollection) {
                return ((PersistentCollection) mappingContext.getSource()).wasInitialized();
            }
            if (mappingContext.getSource() instanceof HibernateProxy) {
                LazyInitializer lazyInitializer = ((HibernateProxy) mappingContext.getSource()).getHibernateLazyInitializer();
                return lazyInitializer.isUninitialized();
            }
            return true;
        });
        return modelMapper;
    }
}
