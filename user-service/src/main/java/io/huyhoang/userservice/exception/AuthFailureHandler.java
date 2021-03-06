package io.huyhoang.userservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.huyhoang.userservice.dto.UserError;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class AuthFailureHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException e) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        UserError userError = new UserError(Collections.singletonList("Unable to authenticate"));
        new ObjectMapper().writeValue(response.getOutputStream(), userError);
    }
}
