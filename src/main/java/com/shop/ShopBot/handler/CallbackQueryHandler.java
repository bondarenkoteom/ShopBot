package com.shop.ShopBot.handler;

import com.shop.ShopBot.bot.keyboards.InlineKeyboard;
import com.shop.ShopBot.constant.MessageEnum;
import com.shop.ShopBot.handler.inline_button_handler.InlineButtonHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.IOException;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {

    InlineKeyboard inlineKeyboard;

    InlineButtonHandler inlineButtonHandler;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) throws IOException {

        String chatId = buttonQuery.getMessage().getChatId().toString();
        Integer messageId = buttonQuery.getMessage().getMessageId();
        String data = buttonQuery.getData();

        InlineKeyboardMarkup helpSell = inlineKeyboard.getInlineNextPageButton("HELP_SELL");
        InlineKeyboardMarkup helpBuyer = inlineKeyboard.getInlineNextPageButton("HELP_BUYER");
        InlineKeyboardMarkup helpBuyer1 = inlineKeyboard.getInlineNextPageButton("HELP_BUYER");


        return switch (data) {
            case "HELP_BTC" -> inlineButtonHandler.getWhatIsBTCMessage(buttonQuery, helpSell, chatId, messageId);
            case "HELP_SELL" -> inlineButtonHandler.getWhatCanISellMessage(buttonQuery, helpBuyer, chatId, messageId);
            case "HELP_BUYER" ->
                    inlineButtonHandler.getBuyersFeaturesMessage(buttonQuery, helpBuyer1, chatId, messageId);
            default -> new SendMessage(chatId, MessageEnum.UNKNOWN_MESSAGE.getMessage());
        };

    }
}
