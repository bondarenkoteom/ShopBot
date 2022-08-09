package com.shop.ShopBot.handler;

import com.shop.ShopBot.bot.keyboards.InlineKeyboard;
import com.shop.ShopBot.constant.MessageEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.IOException;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {

    InlineKeyboard inlineKeyboard;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) throws IOException {
        String chatId = buttonQuery.getMessage().getChatId().toString();
        Integer messageId = buttonQuery.getMessage().getMessageId();

        String data = buttonQuery.getData();

        if (data.equals("HELP_BTC")) {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.WHAT_IS_BTC.getMessage());
            editMessage.setReplyMarkup(inlineKeyboard.getInlineNextPageButton("HELP_SELL"));
            return editMessage;
        } else if (data.equals("HELP_SELL")) {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.WHAT_CAN_I_SELL.getMessage());
            editMessage.setReplyMarkup(inlineKeyboard.getInlineNextPageButton("HELP_BUYER"));
            return editMessage;
        } else if (data.equals("HELP_BUYER")) {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.WHAT_IS_BTC.getMessage());
            editMessage.setText(MessageEnum.BUYERS_FEATURES.getMessage());
            editMessage.setReplyMarkup(inlineKeyboard.getInlineNextPageButton("HELP_BUYER"));
            return editMessage;
        }

        return new SendMessage(chatId, "Callback");

    }

}
