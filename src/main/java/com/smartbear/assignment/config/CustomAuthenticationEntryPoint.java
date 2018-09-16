package com.smartbear.assignment.config;

import com.google.gson.Gson;
import com.smartbear.assignment.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    private Gson gson = new Gson();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx)
            throws IOException, ServletException {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        Response eR = new Response();
        eR.setCode(HttpStatus.UNAUTHORIZED.value());
        eR.setDescrition("Not an Authorized User! Access Forbidden!");
        response.getWriter().print(this.gson.toJson(eR));
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("Tonmoy");
        super.afterPropertiesSet();
    }
}
