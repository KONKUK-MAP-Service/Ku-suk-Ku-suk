package com.cona.KUsukKusuk.global.controller;

import com.cona.KUsukKusuk.global.dto.HealthResponse;
import com.cona.KUsukKusuk.global.response.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NetworkController {
    @GetMapping("/health")
    @Operation(summary = "네트워크 상태", description = "현재 네트워크 정상이면 200 코드를 반환합니다.")
    public HttpResponse<HealthResponse> healthCheck() {

        return HttpResponse.okBuild(HealthResponse.from("HTTP 상태 정상"));
    }
}
