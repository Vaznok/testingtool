package com.epam.rd.security;

import com.epam.rd.error.GenericResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.epam.rd.util.Constants.Headers.TOKEN;


public class RestAuthenticationFilter extends OncePerRequestFilter {

    private TokenService tokenService = new TokenService();

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String token = req.getHeader(TOKEN);
        try {
            Authentication authentication = tokenService.retrieve(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(req, res);
        }catch (RuntimeException ex){
            logger.error("403 Status Code You are not logged in!");
            final GenericResponse bodyOfResponse = new GenericResponse("You are not logged in!", ex.getClass().getSimpleName());
            res.setStatus(HttpStatus.FORBIDDEN.value());
            res.getWriter().write(convertObjectToJson(bodyOfResponse));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
