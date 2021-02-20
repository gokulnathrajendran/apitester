package com.camelrest.apitester;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;

@SpringBootApplication
public class ApitesterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApitesterApplication.class, args);
	}

}
