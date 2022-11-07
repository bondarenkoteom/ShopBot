package com.shop.ShopBot.handlers.callback_query.byuer_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@BotCommand(command = "PURCHASES", type = MessageType.CALLBACK_QUERY)
public class PurchasesHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        String chatId = callbackQuery.getMessage().getChatId().toString();
        Integer messageId = callbackQuery.getMessage().getMessageId();

        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("✅ 7878dsv (1 day ago)", "PURCHASE_1"));
        rowList.add(getButtonList("✅ lk78dsv (1 day ago)", "PURCHASE_2"));
        rowList.add(getButtonList("❌ 68c8dsv (4 day ago)", "PURCHASE_3"));
        rowList.add(getButtonList("❌ o878dsv (3 day ago)", "PURCHASE_4"));
        rowList.add(getButtonList("✅ d87fdsv (2 day ago)", "PURCHASE_5"));
        rowList.add(getButtonList("✅ hy78dsv (1 day ago)", "PURCHASE_6"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Your last deals list");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);

        
    }
}
