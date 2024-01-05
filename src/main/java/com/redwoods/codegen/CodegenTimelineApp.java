package com.redwoods.codegen;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@OpenAPIDefinition(servers = {@Server(url = "/codegenTimeLine/v1", description = "default url")})
@Configuration
@SpringBootApplication
public class CodegenTimelineApp implements CommandLineRunner {

	@Autowired
	private CodeGenerator codeGenerator;

	public static void main(String[] args) {
		SpringApplication.run(CodegenTimelineApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		codeGenerator.generateCRUDCode("Timeline");

	}

	@Bean
	public OpenAPI userMicroserviceOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Timeline Service API").description("Timeline Service APIs").version("1.0"));
	}
}
