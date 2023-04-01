package com.marketplace.controller.internal_api;

import com.marketplace.database.model.Product;
import com.marketplace.database.service.ProductImageService;
import com.marketplace.database.service.ProductService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static com.marketplace.utils.Values.isNotEmpty;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;


    @RequestMapping(value = "/api/v1/product", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Optional<Product> productGet(@RequestParam Long productId) {
        return productService.getById(productId);
    }

    @RequestMapping(value = "/api/v1/product", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void productUpdate(@RequestBody Product product) {
        productService.save(product);
    }

    @RequestMapping(value = "/api/v1/product", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void productDelete(@RequestParam Long productId) {
        productService.deleteById(productId);
    }

    @RequestMapping(value = "/api/v1/products", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Page<Product> products(@ParameterObject Pageable pageable,
                           @RequestParam(required = false) Long productId,
                           @RequestParam(required = false) Long sellerId,
                           @RequestParam(required = false) String name) {
        if (productId != null) {
            return productService.findById(productId, pageable);
        } else if(sellerId != null) {
            return productService.getAllProducts(sellerId, pageable);
        } else if(isNotEmpty(name)) {
            return productService.getAllProducts(name, pageable);
        } else {
            return productService.getAllProducts(pageable);
        }
    }

    @RequestMapping(value = "/api/v1/product/editing", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    Optional<Product> productEditingGet(@RequestParam Long ownerId) {
        return productService.getEditingProductByOwnerId(ownerId);
    }

    @RequestMapping(value = "/api/v1/product/editing", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void productEditingDelete(@RequestParam Long ownerId) {
        productService.deleteAllEditing(ownerId);
    }

//    @PostMapping(value = "/xuy", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Mono<Void> upload(@RequestPart Mono<FilePart> fileParts) {
//        return fileParts.doOnNext(c -> {
//            File convFile = new File(c.filename());
//            c.transferTo(convFile);
//            ProductImage productImage = new ProductImage();
//            try {
//                productImage.setImage(Files.readAllBytes(convFile.toPath()));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
////            productImageService.save(productImage);
//        }).then();
//    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public void save(@RequestParam(value = "file") MultipartFile file) {
        System.out.println();
    }
}
