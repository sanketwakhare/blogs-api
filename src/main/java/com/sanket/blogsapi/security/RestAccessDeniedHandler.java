package com.sanket.blogsapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanket.blogsapi.common.dtos.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class is responsible for handling access denied exception
 */
@Component(value = "restAccessDeniedHandler")
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * @param request               that resulted in an <code>AccessDeniedException</code>
     * @param response              so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException      if an input or output exception occurs
     * @throws ServletException if a servlet-specific exception occurs
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setErrorMessage("Access Denied");
        OutputStream out = response.getOutputStream();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, errorResponseDTO);
        out.flush();
        throw new ServletException("Access Denied");
    }
}
