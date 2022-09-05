package com.shop.ShopBot.handler;

import com.shop.ShopBot.ShopBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

@Component
public class TelegramFacade {

    private MessageHandler messageHandler;

    private CallbackQueryHandler callbackQueryHandler;

    @Autowired
    ShopBot shopBot;

    public TelegramFacade(MessageHandler messageHandler, CallbackQueryHandler callbackQueryHandler,
                          @Lazy ShopBot shopBot) {
        this.messageHandler = messageHandler;
        this.callbackQueryHandler = callbackQueryHandler;
        this.shopBot = shopBot;

    }

    public BotApiMethod<?> handleUpdate(Update update) throws IOException {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return callbackQueryHandler.processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return messageHandler.answerMessage(update.getMessage());
            } else if (replyMessage != null) {
                return messageHandler.handleInputMessage(update.getMessage());
            }
        }
        return null;
    }
}
