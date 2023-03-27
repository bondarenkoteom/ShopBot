package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import com.marketplace.entity.Purchase;
import com.marketplace.entity.User;
import com.marketplace.model.PurchaseForm;
import com.marketplace.model.PurchasesModel;
import com.marketplace.model.UserForm;
import com.marketplace.model.UsersModel;
import com.marketplace.requests.PurchaseRequest;
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

@Controller
public class AdminController {

    private static final Integer PAGE_SIZE = 20;

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

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String customers(Model model, @RequestParam("page") Optional<Integer> page) {

        int currentPage = page.filter(p -> p > 0).orElse(1);

        Page<User> userList = httpCoreInterface.usersGet(currentPage - 1, PAGE_SIZE, new String[]{}, null);

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
        List<User> databaseUsers = httpCoreInterface.usersGet(0, PAGE_SIZE, new String[]{}, ids).toList();

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

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String orders(Model model, @RequestParam("page") Optional<Integer> page) {

        int currentPage = page.filter(p -> p > 0).orElse(1);

        Page<Purchase> purchaseList = httpCoreInterface.purchases(currentPage - 1, PAGE_SIZE, new String[]{}, new PurchaseRequest());

        int totalPages = purchaseList.getTotalPages();

        if (totalPages > 0) {
            int skip = Math.max(currentPage - 2, 0);
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .skip(skip)
                    .limit(3)
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        PurchaseForm purchaseForm = new PurchaseForm();
        purchaseForm.setPurchasesModels(PurchasesModel.of(purchaseList.toList()));

        model.addAttribute("fromCount", getGlobalPosition(currentPage, PAGE_SIZE, 1));
        model.addAttribute("toCount", getGlobalPosition(currentPage, PAGE_SIZE, purchaseList.getNumberOfElements()));
        model.addAttribute("ofCount", purchaseList.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("purchasesForm", purchaseForm);
        return "orders";
    }

    private static int getGlobalPosition(int page, int itemsOnPage, int positionOnPage) {
        return (page * itemsOnPage) - (itemsOnPage - positionOnPage);
    }
}
