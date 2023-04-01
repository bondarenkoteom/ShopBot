package com.marketplace.handlers.callback_query.vendor_panel.manage_my_lots;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.Trigger;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Product;
import com.marketplace.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@BotCommand(command = "SET_LOT_ITEMS .*", type = MessageType.CALLBACK_QUERY)
public class SetLotItemsHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Product> productOptional = httpCoreInterface.productGet(Long.valueOf(keys.get("i")));

        if (productOptional.isPresent()) {
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);

            payload.setText("Send text file with items you want to sell");
            bot.process(payload);

            setTriggerValue(update, Trigger.EDIT_PRODUCT_ITEMS);

            httpCoreInterface.productEditingDelete(update.getCallbackQuery().getFrom().getId());

            Product product = productOptional.get();
            product.setIsEditing(true);
            httpCoreInterface.productUpdate(product);
        }
    }
}