package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@BotCommand(command = "SET_LOT_NAME_\\d+", type = MessageType.CALLBACK_QUERY)
public class SetLotNameHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String productId = update.getCallbackQuery().getData().replace("SET_LOT_NAME_", "");
        Optional<Product> productOptional = productService.getById(Long.valueOf(productId));

        if (productOptional.isPresent()) {
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);

            payload.setText("Please enter the short and clear name of your lot. Try not to use more than 100 symbols.");
            bot.process(payload);

            setTriggerValue(update, Trigger.EDIT_PRODUCT_NAME);
            productService.deleteAllEditing();

            Product product = productOptional.get();
            product.setEditing(true);
            productService.save(product);
        }
    }
}