package com.tfs.demo.tfs_crud_demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(true)
                .apiInfo(new ApiInfoBuilder()
                        .title("TFS APi Documentation Version 2023")
                        .description("Swagger API Documentation for TFS System")
                        .contact(new Contact("Nam Nguyen Hoang","https://www.facebook.com/summerless14","nguyennam279.cl@gmail.com"))
                        .version("2.0")
                        .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tfs"))
                .paths(PathSelectors.any()).build();
    }
}
