package com.JavaTech.PointOfSales.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public String userAccess() {
        return "USER Board.";
    }

    @GetMapping("/admin")
    @ResponseBody
    @PreAuthorize("hasAuthority('ADMIN')")
    public String adminAccess() {
        return "ADMIN Board.";
    }
}
