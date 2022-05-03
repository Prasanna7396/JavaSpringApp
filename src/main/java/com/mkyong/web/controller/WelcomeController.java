package com.mkyong.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Date;

//Simple controller class
@Controller
public class WelcomeController {

    private final Logger logger = LoggerFactory.getLogger(WelcomeController.class);

    @GetMapping("/")
    public String index(Model model) {
        logger.debug("Welcome to our web page ..");
        model.addAttribute("msg", getMessage());
        model.addAttribute("env", getEnv());
	model.addAttribute("today", new Date());
        return "index";

    }
    //Methods for controller class
    public String getMessage() {
        return "Hello World - Prasanna welcomes you.";
    }
    public String getEnv() {
	return "main";		    
    }

}
