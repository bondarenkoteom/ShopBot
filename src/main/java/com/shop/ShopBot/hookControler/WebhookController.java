package com.shop.ShopBot.hookControler;

import com.shop.ShopBot.Bot;
import com.shop.ShopBot.database.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@RestController
@AllArgsConstructor
public class WebhookController {
//    private final Bot shopBot;

    @Autowired
    ProductRepository productRepository;

//    @PostMapping("/")
//    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
//        return shopBot.onWebhookUpdateReceived(update);
//    }

//    @GetMapping(value = "/get-image-with-media-type",
//            produces = MediaType.IMAGE_JPEG_VALUE)
//    public @ResponseBody byte[] getImage() throws IOException {
//        return productRepository.getProductById(9L).getImage();
//    }
}

