package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import com.marketplace.entity.Product;
import com.marketplace.entity.Purchase;
import com.marketplace.model.ProductForm;
import com.marketplace.model.ProductsModel;
import com.marketplace.model.PurchaseForm;
import com.marketplace.model.PurchasesModel;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.marketplace.utils.PageUtils.getGlobalPosition;
import static com.marketplace.utils.Values.toLong;

@Controller
public class ProductsController {

    private static final Integer PAGE_SIZE = 20;

    @Autowired
    private HttpCoreInterface httpCoreInterface;

    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public String products(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam(value = "search", required = false) String search) {

        int currentPage = page.filter(p -> p > 0).orElse(1);

        Page<Product> productList = httpCoreInterface.products(currentPage - 1, PAGE_SIZE, new String[]{}, null, null, search);

        int totalPages = productList.getTotalPages();

        if (totalPages > 0) {
            int skip = Math.max(currentPage - 2, 0);
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .skip(skip)
                    .limit(3)
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        ProductForm productForm = new ProductForm();
        productForm.setProductsModels(ProductsModel.of(productList.toList()));

        model.addAttribute("fromCount", getGlobalPosition(currentPage, PAGE_SIZE, 1));
        model.addAttribute("toCount", getGlobalPosition(currentPage, PAGE_SIZE, productList.getNumberOfElements()));
        model.addAttribute("ofCount", productList.getTotalElements());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("productsForm", productForm);
        return "products";
    }

    @Value("classpath:2.jpg")
    Resource resourceFile;

    @RequestMapping(value = "/image/display/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void showImage(Model model, @PathVariable("id") Long id, HttpServletResponse response) throws IOException {

        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(resourceFile.getContentAsByteArray());
        response.getOutputStream().close();
    }

}
