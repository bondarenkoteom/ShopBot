package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import com.marketplace.entity.Purchase;
import com.marketplace.model.PurchaseForm;
import com.marketplace.model.PurchasesModel;
import com.marketplace.utils.Values;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.marketplace.utils.PageUtils.getGlobalPosition;
import static com.marketplace.utils.Values.toLong;

@Controller
public class OrdersController {

    private static final Integer PAGE_SIZE = 20;

    @Autowired
    private HttpCoreInterface httpCoreInterface;

    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public String orders(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(value = "search", required = false) String search) {

        int currentPage = page.filter(p -> p > 0).orElse(1);

        Page<Purchase> purchaseList = httpCoreInterface.purchases(currentPage - 1, PAGE_SIZE, new String[]{}, null, toLong(search));

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

}
