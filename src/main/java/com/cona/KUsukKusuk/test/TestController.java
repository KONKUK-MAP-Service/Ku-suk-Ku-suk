package com.cona.KUsukKusuk.test;

import com.cona.KUsukKusuk.global.response.HttpResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deploytest")
public class TestController {

    @GetMapping("/health")
    public HttpResponse<String> healthCheck() {
        return HttpResponse.okBuild("배포 성공!");
    }



}
