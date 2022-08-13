package com.shop.ShopBot.handler;

import com.shop.ShopBot.handler.inline_button_handler.InlineButtonHandler;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class CallbackQueryHandler {

    InlineButtonHandler inlineButtonHandler;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {

        return switch (buttonQuery.getData()) {
            case "HELP_BTC" -> inlineButtonHandler.getWhatIsBTCMessage(buttonQuery);
            case "HELP_SELL" -> inlineButtonHandler.getWhatCanISellMessage(buttonQuery);
            case "HELP_BUYER" -> inlineButtonHandler.getBuyersFeaturesMessage(buttonQuery);
            case "BUYER_PANEL_PURCHASES" -> inlineButtonHandler.getPurchases(buttonQuery);
            case String s && s.startsWith("PURCHASE_") -> inlineButtonHandler.getPurchaseInfo(buttonQuery);
            default -> inlineButtonHandler.getErrorMessage(buttonQuery);
        };

    }
}


