package com.shop.ShopBot.entity;

import com.shop.ShopBot.constant.SendMethod;
import lombok.Data;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Data
public class Payload {
    private Integer messageId;
    private String chatId;
    private String text;
    private InlineKeyboardMarkup keyboardMarkup;
    private SendMethod sendMethod;
    private String parseMode;
    private InputFile document;

    private String fileId;

    public Payload(Update update) {
        if (update.hasCallbackQuery()) {
            messageId = update.getCallbackQuery().getMessage().getMessageId();
            chatId = update.getCallbackQuery().getMessage().getChatId().toString();
        } else {
            messageId = update.getMessage().getMessageId();
            chatId = update.getMessage().getChatId().toString();
        }
    }
}
