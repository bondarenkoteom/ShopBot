package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "DELETE_LOT_\\d+", type = MessageType.CALLBACK_QUERY)
public class DeleteLotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String productId = update.getCallbackQuery().getData().replace("DELETE_LOT_", "");

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.DELETE);
        bot.process(payload);

        productService.deleteById(Long.valueOf(productId));

        payload.setText("Your lot is completely deleted.");
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        bot.process(payload);
    }
}
