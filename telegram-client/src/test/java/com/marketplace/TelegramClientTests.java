package com.marketplace;

import com.marketplace.client.HttpInterface;
import com.marketplace.client.WalletClient;
import com.marketplace.database.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class TelegramClientTests {

    @Autowired
    ProductService productService;

    @Test
    void contextLoads() {
        WalletClient webClient = new WalletClient(WebClient.builder().baseUrl("http://localhost:5555").build());
        HttpInterface httpInterface = webClient.getHttpInterface();
        System.out.println(httpInterface.getBooks());
    }

}
