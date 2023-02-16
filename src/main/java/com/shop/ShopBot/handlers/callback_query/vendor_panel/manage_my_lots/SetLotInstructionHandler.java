package com.shop.ShopBot.handlers.callback_query.vendor_panel.manage_my_lots;

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
@BotCommand(command = "SET_LOT_INSTRUCTION .*", type = MessageType.CALLBACK_QUERY)
public class SetLotInstructionHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Product> productOptional = productService.getById(Long.valueOf(keys.get("i")));

        if (productOptional.isPresent()) {
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);

            payload.setText("Send text of instructions how to use your product");
            bot.process(payload);

            setTriggerValue(update, Trigger.EDIT_PRODUCT_INSTRUCTION);
            productService.deleteAllEditing();

            Product product = productOptional.get();
            product.setEditing(true);
            productService.save(product);
        }
    }

}
