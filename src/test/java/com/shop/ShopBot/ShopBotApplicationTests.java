package com.shop.ShopBot;

import com.shop.ShopBot.database.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShopBotApplicationTests {

    @Autowired
    ProductService productService;

    @Test
    void contextLoads() {
//        System.out.println(productService.getInformationAboutProduct());
    }

}
