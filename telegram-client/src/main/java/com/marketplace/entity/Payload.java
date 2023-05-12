package com.marketplace.entity;

import com.marketplace.constant.SendMethod;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Data
@NoArgsConstructor
public class Payload {
    private Integer messageId;
    private String chatId;
    private String text;
    private InlineKeyboardMarkup keyboard;
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
