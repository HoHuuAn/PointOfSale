package com.JavaTech.PointOfSales.security;

import com.JavaTech.PointOfSales.security.service.UserDetailsImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        if( userDetails.isFirstLogin() ){
            System.out.println("The user " + userDetails.getAuthorities() + " has logged in.");
            response.sendRedirect("/change-password-first-time");
        } else {
            response.sendRedirect("/");
        }
    }
}