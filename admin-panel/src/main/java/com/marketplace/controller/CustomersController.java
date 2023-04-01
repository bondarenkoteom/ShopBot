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

import static com.marketplace.utils.PageUtils.getGlobalPosition;

@Controller
public class CustomersController {

    private static final Integer PAGE_SIZE = 20;

    @Autowired
    private HttpCoreInterface httpCoreInterface;

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String customers(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(value = "search", required = false) String search) {

        int currentPage = page.filter(p -> p > 0).orElse(1);

        Page<User> userList = httpCoreInterface.usersGet(currentPage - 1, PAGE_SIZE, new String[]{}, null, search);

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

        UserForm usersForm = new UserForm();
        usersForm.setUsersModels(UsersModel.of(userList.toList()));

        model.addAttribute("fromCount", getGlobalPosition(currentPage, PAGE_SIZE, 1));
        model.addAttribute("toCount", getGlobalPosition(currentPage, PAGE_SIZE, userList.getNumberOfElements()));
        model.addAttribute("ofCount", userList.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("usersForm", usersForm);
        return "customers";
    }

    @RequestMapping(value = "/customers", method = RequestMethod.POST)
    public String saveCustomers(@ModelAttribute UserForm userForm, RedirectAttributes redirectAttributes) {

        List<User> checkedUsers = userForm.getUsersModels().stream().filter(UsersModel::getChecked).map(UsersModel::getUser).toList();
        List<Long> ids = checkedUsers.stream().map(User::getId).toList();
        List<User> databaseUsers = httpCoreInterface.usersGet(0, PAGE_SIZE, new String[]{}, ids, null).toList();

        checkedUsers.forEach(u -> {
            Optional<User> optionalUser = databaseUsers.stream().filter(us -> us.getId().equals(u.getId())).findFirst();
            if (optionalUser.isPresent()) {
                User uu = optionalUser.get();
                uu.setBalance(u.getBalance());
                uu.setStatus(u.getStatus());
                httpCoreInterface.userUpdate(uu);
            }
        });
        redirectAttributes.addFlashAttribute("alert", true);
        return "redirect:/customers";
    }

}
