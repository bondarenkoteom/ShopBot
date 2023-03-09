package com.marketplace;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.client.HttpCoreInterface;
import com.marketplace.constant.Category;
import com.marketplace.constant.ProductStatus;
import com.marketplace.database.model.Product;
import com.marketplace.database.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@SpringBootTest
class TelegramClientTests {

    @Autowired
    private HttpCoreInterface httpCoreInterface;

    @Test
    void contextLoads() throws IOException, InterruptedException, URISyntaxException {
        Product product = new Product();
        product.setId(1L);
        product.setCategory(Category.ALL);
        product.setDescription("dcv");
        product.setEditing(false);
        product.setPrice(1.0);
        product.setInstruction("sss");
        product.setRatingBad(1);
        product.setRatingGood(2);
        product.setImageId("Sss");
        product.setOwnerId(1L);
        product.setStatus(ProductStatus.ACTIVE);
        product.setProductName("adsfadf");


//        HttpClient httpClient = HttpClient.newHttpClient();
//        HttpRequest httpRequest = HttpRequest.newBuilder()
//                .uri(new URI("http://localhost:4230/api/v1/product"))
//                .header("Content-Type", "application/json")
//                .version(HttpClient.Version.HTTP_2)
//                .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(product)))
//                .build();
//
//        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
//        System.out.println(httpResponse.version());
//        System.out.println(httpResponse.body());
//        System.out.println(httpResponse.statusCode());


        System.out.println(httpCoreInterface.deleteProduct(true));
    }

}
