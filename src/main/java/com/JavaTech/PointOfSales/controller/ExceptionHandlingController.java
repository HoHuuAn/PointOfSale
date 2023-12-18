package com.JavaTech.PointOfSales.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionHandlingController implements org.springframework.boot.web.servlet.error.ErrorController{



    @RequestMapping("/access-denied")
    public String denied(){
        return "/error/pages-error-403";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (statusCode != null) {
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // Handle 404 error
                model.addAttribute("errorMessage", "Page not found");
                return "/error/pages-error-404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // Handle 500 error
                model.addAttribute("errorMessage", "An unexpected error occurred");
                return "/error/pages-error-500";
            }
        }
        // Default error handling
        model.addAttribute("errorMessage", "An error occurred");
        return "/error/pages-error-default";
    }
}
