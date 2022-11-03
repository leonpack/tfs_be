package com.tfs.demo.tfs_crud_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
public class TfsCrudDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TfsCrudDemoApplication.class, args);
    }
}
