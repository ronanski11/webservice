package com.ronanski11.demo.webservice;

import java.io.FileNotFoundException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebserviceApplication {

	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(WebserviceApplication.class, args);
	}

}
