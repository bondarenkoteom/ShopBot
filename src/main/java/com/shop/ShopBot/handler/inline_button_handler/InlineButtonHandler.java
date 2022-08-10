package com.shop.ShopBot.handler.inline_button_handler;

import com.shop.ShopBot.constant.MessageEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineButtonHandler {

    public BotApiMethod<?> getWhatIsBTCMessage(CallbackQuery buttonQuery, InlineKeyboardMarkup inlineKeyboard, String chatId, Integer messageId) {
        if (buttonQuery.getMessage().getText().equals(MessageEnum.HELP_MESSAGE.getMessage())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageEnum.WHAT_IS_BTC.getMessage());
            sendMessage.setReplyMarkup(inlineKeyboard);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.WHAT_IS_BTC.getMessage());
            editMessage.setReplyMarkup(inlineKeyboard);
            return editMessage;
        }
    }

    public BotApiMethod<?> getWhatCanISellMessage(CallbackQuery buttonQuery, InlineKeyboardMarkup inlineKeyboard, String chatId, Integer messageId) {
        if (buttonQuery.getMessage().getText().equals(MessageEnum.HELP_MESSAGE.getMessage())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageEnum.WHAT_CAN_I_SELL.getMessage());
            sendMessage.setReplyMarkup(inlineKeyboard);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.WHAT_CAN_I_SELL.getMessage());
            editMessage.setReplyMarkup(inlineKeyboard);
            return editMessage;
        }
    }

    public BotApiMethod<?> getBuyersFeaturesMessage(CallbackQuery buttonQuery, InlineKeyboardMarkup inlineKeyboard, String chatId, Integer messageId) {
        if (buttonQuery.getMessage().getText().equals(MessageEnum.HELP_MESSAGE.getMessage())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageEnum.BUYERS_FEATURES.getMessage());
            sendMessage.setReplyMarkup(inlineKeyboard);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.BUYERS_FEATURES.getMessage());
            editMessage.setReplyMarkup(inlineKeyboard);
            return editMessage;
        }
    }

}
