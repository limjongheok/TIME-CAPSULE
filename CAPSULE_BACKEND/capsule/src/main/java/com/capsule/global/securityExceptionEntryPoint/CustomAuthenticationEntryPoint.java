package com.capsule.global.securityExceptionEntryPoint;

import com.capsule.global.exception.ErrorResponse;
import com.capsule.global.exception.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ExceptionMessage exception = (ExceptionMessage) request.getAttribute("exception");
        ExceptionMessage exceptionMessage;

        log.debug("log: exception: {}", exception);

        if(exception.equals(ExceptionMessage.EXPIREDJWTEXCEPTION)){
            exceptionMessage = ExceptionMessage.EXPIREDJWTEXCEPTION;
            setResponse(response,exceptionMessage);
        }

        if(exception.equals(ExceptionMessage.NOTVALIDJWTEXCEPTION)){
            exceptionMessage = ExceptionMessage.NOTVALIDJWTEXCEPTION;
            setResponse(response,exceptionMessage);
        }
    }

    private void setResponse(HttpServletResponse response, ExceptionMessage exceptionMessage) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("{ \"message\" : \"" + exceptionMessage.getErrorMessage()
                + "\", \"code\" : \"" +  exceptionMessage.getErrorCode()
                + "\", \"status\" : " + exceptionMessage.getErrorCode()
                + "}");
    }
}
