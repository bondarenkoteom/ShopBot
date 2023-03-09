package com.marketplace.controller;

import com.marketplace.client.BitcoinCoreClient;
import com.marketplace.database.model.Product;
import com.marketplace.database.repository.ProductRepository;
import com.marketplace.database.repository.SearchRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SearchRepository searchRepository;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    List<Product> get() {
        return productRepository.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Product save(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void delete(@RequestParam(value="editing", required=false) Boolean editing,
                @RequestParam(value="id", required=false) Long id) {
        if (editing != null && editing) {
            productRepository.deleteByIsEditingTrue();
        }

        if (id != null) {
            productRepository.deleteById(id);
        }

    }


}
