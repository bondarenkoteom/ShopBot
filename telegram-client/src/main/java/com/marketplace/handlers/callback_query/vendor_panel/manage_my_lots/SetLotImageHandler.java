package com.marketplace.handlers.callback_query.vendor_panel.manage_my_lots;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.Trigger;
import com.marketplace.database.model.Product;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
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