package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import com.marketplace.entity.Product;
import com.marketplace.entity.ProductImage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class ProductsController {

    private static final Integer PAGE_SIZE = 20;

    @Autowired
    private HttpCoreInterface httpCoreInterface;

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public List<Product> products() {
        Page<Product> productList = httpCoreInterface.products(0, PAGE_SIZE, new String[]{}, null, null, "");
        return productList.toList();
    }

    @CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
    @RequestMapping(value = "/image/display/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void showImage(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        ProductImage productImage = httpCoreInterface.getImage(id).getBody();
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(productImage.getImage().array());
        response.getOutputStream().close();
    }

}
