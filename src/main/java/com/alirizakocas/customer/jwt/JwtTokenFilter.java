package com.alirizakocas.customer.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // Bearer token şeklinde geliyor
        final String autHeader = httpServletRequest.getHeader("Authorization");
        String username = null;
        String token = null;

        // authHeader varsa ve Bearer içeriyorsa
        if(autHeader != null && autHeader.contains("Bearer")){
            token =autHeader.substring(7); // Bearer kısmını kaldırdık
            try{
                username = tokenManager.getUsernameFromToken(token);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        if(username != null && token != null && SecurityContextHolder.getContext().getAuthentication() == null){
            if(tokenManager.tokenValidate(token)){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,null,new ArrayList<>());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);// spring com.jwt.springbootjwt.security contexte bu auth tokeni verdik.auth manager UserDetails servise username'i gönderip, kullanıcı context holder'ına atayacaktır

            }
        }

        filterChain.doFilter(httpServletRequest,httpServletResponse);

    }
}
