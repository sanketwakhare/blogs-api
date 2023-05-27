package com.sanket.blogsapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanket.blogsapi.common.dtos.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is responsible for handling authentication exception
 */
@Component(value = "delegatedAuthenticationEntryPoint")
public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handle authentication exception
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException      if an input or output exception occurs
     * @throws ServletException if a servlet-specific exception occurs
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        OutputStream out = response.getOutputStream();
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO("Unauthorized to access this resource");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, errorResponseDTO);
        out.flush();
        throw new ServletException("Unauthorized to access this resource");
    }
}
