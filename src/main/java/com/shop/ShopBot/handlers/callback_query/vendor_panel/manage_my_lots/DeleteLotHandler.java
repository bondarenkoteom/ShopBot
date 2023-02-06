package com.shop.ShopBot.handlers.callback_query.vendor_panel.manage_my_lots;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "DELETE_LOT .*", type = MessageType.CALLBACK_QUERY)
public class DeleteLotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.DELETE);
        bot.process(payload);

        productService.deleteById(Long.valueOf(keys.get("i")));

        payload.setText("Your lot is completely deleted.");
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        bot.process(payload);
    }
}
