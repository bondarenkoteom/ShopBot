package com.shop.ShopBot.handlers.callback_query.user_settings;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "SET_USERNAME", type = MessageType.CALLBACK_QUERY)
public class SetUsernameHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        setTriggerValue(update, Trigger.USERNAME);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);

        payload.setText("Enter username");
        bot.process(payload);
    }
}