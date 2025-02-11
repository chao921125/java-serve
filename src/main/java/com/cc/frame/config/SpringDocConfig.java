package com.cc.frame.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import jakarta.validation.Valid;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class SpringDocConfig {
    @Value("${project.name}")
    private String NAME;
    @Value("${project.version}")
    private String VERSION;
    @Value("${project.email}")
    private String EMAIL;
    @Value("${project.url}")
    private String URL;
    @Bean
    public OpenAPI myOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(NAME)
                        .version(VERSION)
                        .description(NAME)
                        .license(new License()
                                .name("许可协议")
                                .url(""))
                        .contact(new Contact()
                                .name(NAME)
                                .email(EMAIL)))
                .externalDocs(new ExternalDocumentation()
                        .description(NAME)
                        .url(URL));
    }

    /**
     * 配置分组 接口
     */
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("api")
                .pathsToMatch("/api/**")
                .build();
    }

    /**
     * 配置分组 后端接口
     */
    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/admin/**")
                .build();
    }
}
