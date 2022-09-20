package com.miniproject.userservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.miniproject.userservice.vo.RequestLogin;
import com.sun.xml.bind.v2.TODO;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response)
            throws AuthenticationException {

        //유저의 이메일과 페스워드를 받는다 try-catch
        try {
            RequestLogin cred = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);
            // cred 를 authentication Manager를 통해 확인 받을 수 있다(return 값). 인자 객체는 UsernamePasswordAuthenticationToken이다.
            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(cred.getEmail(), cred.getPassword(), null)); // TODO 권한에 대해 입력해줄 것이다.


        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
