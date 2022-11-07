package com.shop.ShopBot.handlers.callback_query.user_settings;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "USER_INFO", type = MessageType.CALLBACK_QUERY)
public class UserInfoHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String chatId = callbackQuery.getMessage().getChatId().toString();

        User user = userService.getUser(callbackQuery.getFrom().getId());
        String text;
        if (user == null) {
            text = "Fuck off, scum";
        } else {
            text = String.join("\n", user.getUsername(), user.getId().toString());
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        
    }
}
