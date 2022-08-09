package com.shop.ShopBot.handler;

import com.shop.ShopBot.bot.keyboards.InlineKeyboard;
import com.shop.ShopBot.constant.ButtonNameEnum;
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
            EditMessageText sendMessage = new EditMessageText();
            sendMessage.setMessageId(messageId);
            sendMessage.setChatId(chatId);
            sendMessage.setText(ButtonNameEnum.WALLET.getButtonName());
            sendMessage.setReplyMarkup(inlineKeyboard.getInlineWalletButtons());
            return sendMessage;
        }

        return new SendMessage(chatId, "Callback");

    }

}
