package com.Employee.RestApi.Config;

import com.Employee.RestApi.Entity.EmployeeEntity;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper getModelMapper(){
        ModelMapper modelMapper=new ModelMapper();
        return modelMapper;
    }

}
