package com.spring.turnxcel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.spring.turnxcel.controller", "com.turnxcel.xmlreader", "com.turnxcel.converter"})
public class TurnXCelApplication {

    private static final Logger logger = LoggerFactory.getLogger(TurnXCelApplication.class);

    public static void main(String[] args) {
        logger.info("Starting TurnXCelApplication...");
        SpringApplication.run(TurnXCelApplication.class, args);
    }

}
