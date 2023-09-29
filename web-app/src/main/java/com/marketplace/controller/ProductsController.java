package com.marketplace.controller;

import com.marketplace.client.HttpCoreInterface;
import com.marketplace.entity.Product;
import com.marketplace.entity.ProductImage;
import com.marketplace.models.BuyIds;
import com.marketplace.models.NewProduct;
import com.marketplace.models.SearchItem;
import com.marketplace.models.SearchItemFullInfo;
import com.marketplace.requests.BuyRequest;
import com.marketplace.responses.BuyResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = {"https://shimmering-ganache-46cafe.netlify.appp", "http://localhost:30000"}, maxAge = 3600)
@Controller
public class ProductsController {

    private static final Integer PAGE_SIZE = 10;

    @Autowired
    private HttpCoreInterface httpCoreInterface;

    /**
     * Поиск товаров
     */
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    @ResponseBody
    public List<SearchItem> products(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Integer page) {
        if (page.equals(2)) return List.of();
        return httpCoreInterface.products(0, PAGE_SIZE, new String[]{}, null, null, "")
                .stream().map(product -> {
                    SearchItem searchItem = new SearchItem();
                    searchItem.setId(product.getId());
                    searchItem.setSeller("lemon");
                    searchItem.setReviews(100);
                    searchItem.setRating(3.5);
                    searchItem.setTitle(product.getProductName());
                    searchItem.setPrice(10.99);
                    searchItem.setImageId(product.getProductImageId());
                    return searchItem;
                }).toList();
    }

    /**
     * Подробное инфо о товаре
     */
    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<SearchItemFullInfo> product(@PathVariable("id") Long id) {
        return httpCoreInterface.productGet(id).map(product -> {
            SearchItemFullInfo searchItem = new SearchItemFullInfo();
            searchItem.setId(product.getId());
            searchItem.setSeller("lemon");
            searchItem.setReviews(100);
            searchItem.setRating(3.5);
            searchItem.setTitle(product.getProductName());
            searchItem.setDescription(product.getDescription());
            searchItem.setPrice(10.99);
            searchItem.setImageId(product.getProductImageId());
            searchItem.setSells(122);
            return searchItem;
        });
    }

    /**
     * Получение картинки
     */
    @RequestMapping(value = "/image/display/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void image(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        ProductImage productImage = httpCoreInterface.getImage(id).getBody();
        response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
        response.getOutputStream().write(productImage.getImage().array());
        response.getOutputStream().close();
    }

    /**
     * Создание карточки товара
     */
    @RequestMapping(value = "/product", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<?> createNew(@RequestPart MultipartFile file, @RequestPart NewProduct newProduct) throws IOException {

        final int size = 16 * 1024 * 1024;
        final ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                .build();
        WebClient webClient = WebClient.builder().exchangeStrategies(strategies).build();
        Long productImageId = webClient.post()
                .uri("http://localhost:4230/api/v1/product/image")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(fromByteArray(file.getName(), file.getBytes())))
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        Product product = new Product();
        product.setProductImageId(productImageId);
        product.setProductName(newProduct.getTitle());
        product.setDescription(newProduct.getDescription());
        product.setInstruction(newProduct.getInstruction());
        product.setPrice(newProduct.getPrice());
        product.setCategory(newProduct.getCategories()[0]);
//        product.setOwner();
        httpCoreInterface.productUpdate(product);
        return ResponseEntity.ok(productImageId);
    }

    /**
     * Покупка
     */
    @RequestMapping(value = "/buy", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> buy(@RequestBody BuyIds buyIds) {
        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setUserId(111L);
        buyRequest.setProductId(buyIds.getIds()[0]);
        BuyResponse buyResponse = httpCoreInterface.buy(buyRequest);
        return ResponseEntity.ok(1);
    }

    public MultiValueMap<String, HttpEntity<?>> fromByteArray(String filename, byte[] file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("fileParts", new ByteArrayResource(file)).filename(filename);
        return builder.build();
    }

}
