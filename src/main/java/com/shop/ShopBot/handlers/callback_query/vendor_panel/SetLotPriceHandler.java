package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@BotCommand(command = "SET_LOT_PRICE_\\d+", type = MessageType.CALLBACK_QUERY)
public class SetLotPriceHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String productId = update.getCallbackQuery().getData().replace("SET_LOT_PRICE_", "");
        Optional<Product> productOptional = productService.getById(Long.valueOf(productId));

        if (productOptional.isPresent()) {
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);

            payload.setText("Please enter the price of one key of your lot in BTC. For example  0.0001. Min. value is 0.00001");
            bot.process(payload);

            setTriggerValue(update, Trigger.EDIT_PRODUCT_PRICE);
            productService.deleteAllEditing();

            Product product = productOptional.get();
            product.setEditing(true);
            productService.save(product);
        }
    }
}