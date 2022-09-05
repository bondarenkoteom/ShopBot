package com.shop.ShopBot.database.service;

import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List getInformationAboutProduct() {
        String productName = productRepository.findById(9L).get().getProductName();
        String description = productRepository.findById(9L).get().getDescription();
        String price = productRepository.findById(9L).get().getPrice();

        File outputFile = new File("outputFile.jpg");
        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(productRepository.findById(9L).get().getBytea());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Arrays.asList(productName, description, price, outputFile);
    }
}
