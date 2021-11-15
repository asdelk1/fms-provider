package com.owerp.fmsprovider.config;

import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private final ApplicationContext context;

    public ModelMapperConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public EntityModelMapper getEntityModelMapper(){
        return new EntityModelMapper(this.context);
    }

}
