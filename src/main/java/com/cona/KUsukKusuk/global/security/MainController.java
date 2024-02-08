package com.cona.KUsukKusuk.global.security;

import io.swagger.v3.oas.annotations.Operation;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class MainController {
    @GetMapping("/userinfo")
    @Operation
    public String main() {
        String username= SecurityContextHolder.getContext().getAuthentication()
                .getName();


        return "현재 접속자 아이디 : "+username;
    }
}
