package com.grapplelogic.engine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class GrappleLogicEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrappleLogicEngineApplication.class, args);
	}

}
