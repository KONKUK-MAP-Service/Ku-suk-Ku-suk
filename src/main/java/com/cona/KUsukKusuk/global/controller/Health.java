package com.cona.KUsukKusuk.global.controller;

import com.cona.KUsukKusuk.global.dto.HealthResponse;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Health {
    @GetMapping("/health")
    public HttpResponse<HealthResponse> healthCheck() {

        return HttpResponse.okBuild(HealthResponse.from("HTTP 상태 정상"));
    }
}
