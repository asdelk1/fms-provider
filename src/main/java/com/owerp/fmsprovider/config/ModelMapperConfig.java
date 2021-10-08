package com.owerp.fmsprovider.config;

import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public EntityModelMapper getEntityModelMapper(){
        return new EntityModelMapper();
    }

}
