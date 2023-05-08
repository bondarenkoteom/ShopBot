package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import com.marketplace.entity.User;
import com.marketplace.model.UserForm;
import com.marketplace.model.UsersModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.marketplace.utils.PageUtils.getGlobalPosition;

@Controller
public class ChatController {

    @Autowired
    private HttpCoreInterface httpCoreInterface;

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public String chat(Model model) {

        model.addAttribute("students", Stream.iterate(0, i -> i + 1).limit(20));
        return "chat";
    }

}
