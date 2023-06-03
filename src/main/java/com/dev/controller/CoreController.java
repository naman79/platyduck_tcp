package com.dev.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CoreController {

    @GetMapping
    public String core() {

        System.out.println("core");

        return "core.html";
    }

}
