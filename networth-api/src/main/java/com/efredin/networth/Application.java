package com.efredin.networth;

import com.efredin.networth.balancesheets.BalanceSheetRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public BalanceSheetRepository balanceSheetRepository() {
		return new BalanceSheetRepository();
	}
}

