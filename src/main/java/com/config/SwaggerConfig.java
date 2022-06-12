package com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket postsApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaData());

    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Spring Boot REST API",
                "Post Service owned by JibberJabber",
                "1.0",
                "Terms of service",
                "",
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
    }

}

