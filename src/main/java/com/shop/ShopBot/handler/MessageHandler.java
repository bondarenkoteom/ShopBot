package com.shop.ShopBot.handler;

import com.shop.ShopBot.bot.keyboards.InlineKeyboard;
import com.shop.ShopBot.bot.keyboards.ReplyKeyboard;
import com.shop.ShopBot.constant.ButtonNameEnum;
import com.shop.ShopBot.constant.MessageEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {

    ReplyKeyboard replyKeyboard;
    InlineKeyboard inlineKeyboard;


    public BotApiMethod<?> answerMessage(Message message) {

        String chatId = message.getChatId().toString();

        String inputText = message.getText();

        return switch (inputText) {
            case "/start" -> getStartMessage(chatId);
            case "/help" -> getHelpMessage(chatId);
            case String s && s.equals(ButtonNameEnum.USER_SETTINGS.getButtonName()) -> getUserSettingsMessage(chatId);
            case String s && s.equals(ButtonNameEnum.WALLET.getButtonName()) -> getWalletMessage(chatId);
            case String s && s.equals(ButtonNameEnum.BUYER_PANEL.getButtonName()) -> getBuyerPanelMessage(chatId);
            case String s && s.equals(ButtonNameEnum.VENDOR_PANEL.getButtonName()) -> getVendorPanelMessage(chatId);
            case null -> throw new IllegalArgumentException();
            default -> new SendMessage(chatId, MessageEnum.UNKNOWN_MESSAGE.getMessage());
        };
    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.START_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboard.getMainMenuKeyboard());
        return sendMessage;
    }

    private SendMessage getHelpMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineHelpButtons());
        return sendMessage;
    }

    private SendMessage getUserSettingsMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.SETTINGS_DEFAULT_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineUserSettingsButtons());
        return sendMessage;
    }

    private SendMessage getWalletMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.WALLET_DEFAULT_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineWalletButtons());
        return sendMessage;
    }

    private SendMessage getBuyerPanelMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.BUYER_DEFAULT_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineBuyerPanelButtons());
        return sendMessage;
    }

    private SendMessage getVendorPanelMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.VENDOR_DEFAULT_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineVendorPanelButtons());
        return sendMessage;
    }

}
