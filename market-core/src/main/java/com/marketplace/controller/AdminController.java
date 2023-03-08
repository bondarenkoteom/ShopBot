package com.marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

//    @RequestMapping({ "/index", "/" })
//    public String index() {
//        return "index";
//    }

    @RequestMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @RequestMapping("/customers")
    public String event() {
        return "customers";
    }

    @RequestMapping("/web")
    public String web() {
        return "web";
    }

}
