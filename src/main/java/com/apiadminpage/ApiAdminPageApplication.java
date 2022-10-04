package com.apiadminpage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@EnableSwagger2
public class ApiAdminPageApplication extends SpringBootServletInitializer {

	static{
		try {
			System.setProperty("hostName", InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		SpringApplication.run(ApiAdminPageApplication.class, args);
	}


}
