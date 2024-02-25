package com.spring.turnxcel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.spring.turnxcel.controller", "com.turnxcel.xmlreader", "com.turnxcel.converter"})


public class TurnXCelApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurnXCelApplication.class, args);
	}

}
