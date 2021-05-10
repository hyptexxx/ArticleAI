package com.example.ArticleAI.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MainPageController {
    @RequestMapping(value = {"", "/monitoring", "/fileHistory", "/about", "/settings"}, method = RequestMethod.GET)
    public String catchAllPath () {
        return "main";
    }
}
