package com.crud.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
//@ImportResource("classpath:hibernate/*.hbm.xml")
public class CrudprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudprojectApplication.class, args);
	}

}
