package com.se.service.notification.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Evgeniy Skiba
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public @ResponseBody
    String greeting() {
        return "Hello, World";
    }
}