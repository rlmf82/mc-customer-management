package com.mastercard.microservice.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansUtils {

	@Bean
	public ModelMapper modelMapper() {
	    return new ModelMapper();
	}
	
}