package com.hamitmizrak.bean;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBean {

    @Bean
    public ModelMapper getModelMapperBeanMethod() {
        return new ModelMapper();
    }
}
