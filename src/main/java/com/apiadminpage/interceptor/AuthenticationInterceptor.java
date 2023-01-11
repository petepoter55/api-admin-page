package com.apiadminpage.interceptor;

import com.apiadminpage.exception.ResponseException;
import com.apiadminpage.model.response.Response;
import com.apiadminpage.service.jwt.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final static Logger logger = Logger.getLogger(AuthenticationInterceptor.class);

    @Autowired
    private JWTService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        try {
            logger.info("preHandle start...");
            this.jwtService.checkAccessToken(httpServletRequest.getHeader("token"));
        } catch (ResponseException e) {
            logger.info(e.getMessage(), e);
            httpServletResponse.setHeader("content-type", "application/json");
            PrintWriter out = httpServletResponse.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(new Response(false, e.getMessage(), e.getExceptionCode(), null));
            out.print(json);
            return false;
        }
        logger.info("preHandle end...");
        return true;
    }
}
