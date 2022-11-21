package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@BotCommand(command = "SET_LOT_IMAGE .*", type = MessageType.CALLBACK_QUERY)
public class SetLotImageHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Product> productOptional = productService.getById(Long.valueOf(keys.get("i")));

        if (productOptional.isPresent()) {
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);

            payload.setText("Please enter image of your lot. Use image with size less than 5mb.");
            bot.process(payload);

            setTriggerValue(update, Trigger.EDIT_PRODUCT_IMAGE);
            productService.deleteAllEditing();

            Product product = productOptional.get();
            product.setEditing(true);
            productService.save(product);
        }
    }
}