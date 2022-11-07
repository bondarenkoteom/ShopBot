package com.shop.ShopBot.handlers.callback_query.support;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@BotCommand(command = "HELP_SELL", type = MessageType.CALLBACK_QUERY)
public class HelpSellHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("Next page", "HELP_BUYER"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        if (callbackQuery.getMessage().getText().equals(MessageText.HELP_MESSAGE.text())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageText.BUYERS_FEATURES.text());
            sendMessage.setReplyMarkup(inlineKeyboardMarkup);
            
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageText.BUYERS_FEATURES.text());
            editMessage.setReplyMarkup(inlineKeyboardMarkup);
            //return editMessage;
        }
    }
}
