package com.healthcare.helix.userModule.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/internal/test")
    public String test() {
        return "OK - no auth needed";
    }
}