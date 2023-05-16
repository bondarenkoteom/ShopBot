package com.marketplace.controller;

import com.marketplace.database.jpa.model.Product;
import com.marketplace.database.r2dbc.model.ProductImage;
import com.marketplace.database.service.ProductImageService;
import com.marketplace.database.service.ProductService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.marketplace.utils.Values.isNotEmpty;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductImageService productImageService;

    @Value("classpath:no-content.png")
    Resource resourceFile;


    @RequestMapping(value = "/api/v1/product", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Product> productGet(@RequestParam Long productId) {
        return productService.getById(productId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @RequestMapping(value = "/api/v1/product", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.CREATED)
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
    public @ResponseBody
    ResponseEntity<Product> productEditingGet(@RequestParam Long ownerId) {
        return productService.getEditingProductByOwnerId(ownerId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @RequestMapping(value = "/api/v1/product/editing", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public @ResponseBody
    void productEditingDelete(@RequestParam Long ownerId) {
        productService.deleteAllEditing(ownerId);
    }




    @RequestMapping(value = "/api/v1/product/image/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<ProductImage>> getImage(@PathVariable Long id) {
        return productImageService.findById(id)
                .map(post -> ResponseEntity.ok().body(post))
                .switchIfEmpty(Mono.just(ResponseEntity.of(Optional.of(new ProductImage(resourceFile)))));
    }

    @RequestMapping(value = "/api/v1/product/image", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<?>> saveImage(@RequestPart Mono<FilePart> fileParts) {

        return Mono
                .zip(objects -> {
                            ProductImage productImage = new ProductImage();
                            var filePart = (DataBuffer)objects[0];
                            productImage.setImage(filePart.toByteBuffer());
                            return productImage;
                        },
                        fileParts.flatMap(filePart -> DataBufferUtils.join(filePart.content()))
                )
                .flatMap(productImageService::save)
                .map(saved -> ResponseEntity.of(Optional.of(saved.getId())));

    }

    @RequestMapping(value = "/api/v1/product/image/{id}", method = RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<?>> updateImage(@PathVariable Long id, @RequestPart Mono<FilePart> fileParts) {

        return Mono
                .zip(objects -> {
                            var productImage = (ProductImage) objects[0];
                            var filePart = (DataBuffer)objects[1];
                            productImage.setImage(filePart.toByteBuffer());
                            return productImage;
                        },
                        productImageService.findById(id),
                        fileParts.flatMap(filePart -> DataBufferUtils.join(filePart.content()))
                )
                .flatMap(productImageService::save)
                .map(saved -> ResponseEntity.noContent().build());

    }
}
