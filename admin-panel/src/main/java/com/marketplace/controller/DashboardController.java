package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {

    @Autowired
    private HttpCoreInterface httpCoreInterface;

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

}
