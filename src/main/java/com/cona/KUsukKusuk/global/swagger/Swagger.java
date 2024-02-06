package com.cona.KUsukKusuk.global.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "쿠석쿠석",
                description = "KusukKusuk API 명세서 입니다.",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
public class Swagger {
    //http://localhost:8080/swagger-ui/index.html#/
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/**"};

        return GroupedOpenApi.builder()
                .group("쿠석쿠석 API v1")
                .pathsToMatch(paths)
                .build();
    }
}
