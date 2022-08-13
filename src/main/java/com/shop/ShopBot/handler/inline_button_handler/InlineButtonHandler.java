package com.shop.ShopBot.handler.inline_button_handler;

import com.shop.ShopBot.bot.keyboards.InlineKeyboard;
import com.shop.ShopBot.constant.MessageEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineButtonHandler {

    @Autowired
    private InlineKeyboard inlineKeyboard;

    private String chatId;

    private Integer messageId;

    public BotApiMethod<?> getErrorMessage(CallbackQuery buttonQuery) {
        return new SendMessage(buttonQuery.getMessage().getChatId().toString(), MessageEnum.UNKNOWN_MESSAGE.getMessage());
    }

    public BotApiMethod<?> getWhatIsBTCMessage(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlineNextPageButton("HELP_SELL");
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();
        if (buttonQuery.getMessage().getText().equals(MessageEnum.HELP_MESSAGE.getMessage())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageEnum.WHAT_IS_BTC.getMessage());
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.WHAT_IS_BTC.getMessage());
            editMessage.setReplyMarkup(keyboardMarkup);
            return editMessage;
        }
    }

    public BotApiMethod<?> getWhatCanISellMessage(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlineNextPageButton("HELP_BUYER");
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        if (buttonQuery.getMessage().getText().equals(MessageEnum.HELP_MESSAGE.getMessage())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageEnum.WHAT_CAN_I_SELL.getMessage());
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.WHAT_CAN_I_SELL.getMessage());
            editMessage.setReplyMarkup(keyboardMarkup);
            return editMessage;
        }
    }

    public BotApiMethod<?> getBuyersFeaturesMessage(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlineNextPageButton("HELP_BTC");
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        if (buttonQuery.getMessage().getText().equals(MessageEnum.HELP_MESSAGE.getMessage())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageEnum.BUYERS_FEATURES.getMessage());
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageEnum.BUYERS_FEATURES.getMessage());
            editMessage.setReplyMarkup(keyboardMarkup);
            return editMessage;
        }
    }

    public BotApiMethod<?> getPurchases(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlinePurchasesButtons();
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Your last deals list");
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public BotApiMethod<?> getPurchaseInfo(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlinePurchaseInfoButtons();
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Deal: 743fc92d\n" +
                "\n" +
                "Seller ID: /uCatsell\n" +
                "\n" +
                "Buyer ID: /ub71b71\n" +
                "\n" +
                "Deal started: 1 month ago\n" +
                "  \n" +
                "Deal status changed: 1 month ago\n" +
                "  \n" +
                "Deal current status: Completed");
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

}
