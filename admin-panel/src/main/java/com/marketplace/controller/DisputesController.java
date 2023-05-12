package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.stream.Stream;

@Controller
public class DisputesController {

    @Autowired
    private HttpCoreInterface httpCoreInterface;

    @RequestMapping(value = "/disputes", method = RequestMethod.GET)
    public String disputes(Model model) {

        return "disputes";
    }

}
