package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import com.marketplace.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {

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

    @RequestMapping("/customers")
    public String customers(Model model, @RequestParam("page") Optional<Integer> page) {

        int currentPage = page.filter(p -> p > 0).orElse(1);
        int size = 1;

        Page<User> userList = httpCoreInterface.usersGet(currentPage - 1, size, new String[]{});
        model.addAttribute("users", userList.toList());
        model.addAttribute("fromCount", getGlobalPosition(currentPage, size, 1));
        model.addAttribute("toCount", getGlobalPosition(currentPage, size, userList.getNumberOfElements()));
        model.addAttribute("ofCount", userList.getTotalElements());

        int totalPages = userList.getTotalPages();

        if (totalPages > 0) {
            int skip = Math.max(currentPage - 2, 0);
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .skip(skip)
                    .limit(3)
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        return "customers";
    }

    @RequestMapping("/web")
    public String web() {
        return "web";
    }

    private static int getGlobalPosition(int page, int itemsOnPage, int positionOnPage) {
        return (page * itemsOnPage) - (itemsOnPage - positionOnPage);
    }
}
