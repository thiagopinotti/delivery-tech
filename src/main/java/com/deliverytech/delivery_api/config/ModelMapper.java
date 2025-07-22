package com.deliverytech.delivery_api.config;

import org.springframework.context.annotation.Bean;

public class ModelMapper {
    
    @Bean
    public org.modelmapper.ModelMapper modelMapper() {
        return new org.modelmapper.ModelMapper();
    }
}
