package com.tfs.demo.tfs_crud_demo.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
//@EnableSwagger2
public class SwaggerConfig {

    //DEPRECATED!!

//    @Bean
//    public Docket docket() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .enable(true)
//                .apiInfo(new ApiInfoBuilder()
//                        .title("TFS APi Documentation Version 2023")
//                        .description("Swagger API Documentation for TFS System")
//                        .contact(new Contact("Nam Nguyen Hoang","https://www.facebook.com/summerless14","nguyennam279.cl@gmail.com"))
//                        .version("2.0")
//                        .build())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.tfs"))
//                .paths(PathSelectors.any()).build();
//    }

    @Bean
    public OpenAPI tfsSWAGGER() {
        return new OpenAPI()
                .info(new Info().title("Traditional Food System's API")
                        .description("TFS API DOCUMENTATION")
                        .contact(new Contact().name("Nathan Nguyễn").email("namnhse140816@fpt.edu.vn").url("https://www.facebook.com/summerless14"))
                        .version("v2023.01")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Author GitHub page")
                        .url("https://github.com/leonpack"));
    }
}
