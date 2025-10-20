package com.apishield.core.shieldcore;

import org.springframework.boot.SpringApplication;

public class TestApiShieldCoreApplication {

	public static void main(String[] args) {
		SpringApplication.from(ApiShieldCoreApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
