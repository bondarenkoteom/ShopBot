package com.shop.ShopBot.handlers.callback_query.user_settings;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "SET_USERNAME", type = MessageType.CALLBACK_QUERY)
public class SetUsernameHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        userService.setWaitFor(callbackQuery.getFrom().getId(), Trigger.USERNAME);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Enter username");
    }
}