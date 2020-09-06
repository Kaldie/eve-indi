package com.kaldie.eveindi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Main {

    @GetMapping("/")    
    public String home(@RequestParam(name="name", required=false, defaultValue="lala") String name, Model model) {
        return "main";
    }
}
