package com.cona.KUsukKusuk.global.security;

import com.cona.KUsukKusuk.global.dto.LoginRequest;
import com.cona.KUsukKusuk.global.redis.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper();



    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil=jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        if (!request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            // Content-Type이 "application/x-www-form-urlencoded"인 경우

            String username = obtainUsername(request);
            String password = obtainPassword(request);
            logger.info("추출한 username : "+username);
            logger.info("추출한 비밀번호 : "+password);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            return authenticationManager.authenticate(authToken);
        }

        try {
            // Content-Type이 "application/json"일 경우
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);


            String username = loginRequest.username();
            String password = loginRequest.password();
            logger.info("추출한 username : "+username);
            logger.info("추출한 비밀번호 : " + password);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication)
            throws IOException {

        //UserDetailsS
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();


        String password = customUserDetails.getPassword();
        //AT : 6분
        String accessToken = jwtUtil.createJwt(username, password, 60*60*100L);
        //RT : 7일
        String refreshToken = jwtUtil.createRefreshToken(username, password, 86400000*7L);


        sendTokenResponse(response,accessToken,refreshToken);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException {

        response.getWriter().write("해당 사용자의 아이디나 비밀번호가 옳지 않습니다. 다시 확인해주세요");
        response.setStatus(400);
    }
    private void setResponse(HttpServletResponse response,int status, String message) throws RuntimeException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        response.getWriter().print(message);
    }
    private void sendTokenResponse(HttpServletResponse response, String AT,String RT ) throws IOException {
        String jsonResponse = "{\"accessToken\": \"" +"Bearer " + AT +
                "\", \"refreshToken\": \"" +"Bearer "+ RT + "\"}";

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().print(jsonResponse);
    }
}
