package com.JavaTech.PointOfSales.security;

import com.JavaTech.PointOfSales.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UrlPathHelper;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component("authenticationFailureHandler")
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String failureUrl = "/api/auth/login?error=";
        if (exception instanceof BadCredentialsException) {
            failureUrl += URLEncoder.encode("error", StandardCharsets.UTF_8);
        }
        else if (exception instanceof DisabledException) {

            failureUrl += URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8);
        }
        else if (exception != null  ) {
            failureUrl += URLEncoder.encode(exception.getMessage(), StandardCharsets.UTF_8);
        }

        assert exception != null;
        System.out.println(exception.getMessage());

        super.setDefaultFailureUrl(failureUrl);

    }
}