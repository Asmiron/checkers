package com.cpp.Checkers;

import jakarta.servlet.ServletContext;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.context.ServletContextAware;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class CheckersApplication {
	public static void main(String[] args) {
		SpringApplication.run(CheckersApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
