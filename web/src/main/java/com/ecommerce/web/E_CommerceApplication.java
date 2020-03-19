package com.ecommerce.web;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = { "com.ecommerce.*", "com.ecommerece.controller.inventory",
		"com.ecommerece.controller.shipping" })
@EntityScan(value = { "com.ecommerce.*" })
@EnableJpaRepositories(value = { "com.ecommerce.*" })

public class E_CommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(E_CommerceApplication.class, args);

		Resource r = new ClassPathResource("applicationContext.xml");
		BeanFactory factory = new XmlBeanFactory(r);

		/*
		 * Services v = factory.getBean("proxy", Services.class); v.m();
		 * 
		 * try { v.validate(12); } catch (Exception e) { e.printStackTrace(); }
		 */

	}
}
