

package com.JavaTech.PointOfSales;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PointOfSalesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PointOfSalesApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}




}

























