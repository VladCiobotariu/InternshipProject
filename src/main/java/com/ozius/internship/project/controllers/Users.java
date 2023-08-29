package com.ozius.internship.project.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Users {

    @GetMapping("/hello-world")
    @PreAuthorize("hasRole('CLIENT')")
    public void helloWorld(){
    }

    @GetMapping("/hello-world-v2")
    @PreAuthorize("hasRole('ADMIN')")
    public void helloWorldV2(){
    }

    @GetMapping("/hello-world-v3")
    @PreAuthorize("hasRole('MEGA_ADMIN')")
    public void helloWorldV3(){
    }
}
