package com.shop.ShopBot.handler;

import com.shop.ShopBot.bot.messages.InlineMessage;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.service.UserService;
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

    InlineMessage inlineMessage;

    UserService userService;

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {

        userService.setWaitFor(buttonQuery.getFrom().getId(), Trigger.UNDEFINED);

        return switch (buttonQuery.getData()) {
            case "HELP_BTC" -> inlineMessage.getWhatIsBTCMessage(buttonQuery);
            case "HELP_SELL" -> inlineMessage.getWhatCanISellMessage(buttonQuery);
            case "HELP_BUYER" -> inlineMessage.getBuyersFeaturesMessage(buttonQuery);

            case "BUYER_PANEL_PURCHASES" -> inlineMessage.getPurchasesMessage(buttonQuery);
            case String s && s.startsWith("PURCHASE_") -> inlineMessage.getPurchaseInfoMessage(buttonQuery);

            case "SET_USERNAME" -> inlineMessage.getSetUsernameMessage(buttonQuery);
            case "USER_INFO" -> inlineMessage.getUserInfoMessage(buttonQuery);

            case "VENDOR_PANEL_PRODUCT" -> inlineMessage.getAddGoodsImageMessage(buttonQuery);
//            case "VENDOR_PANEL_INFORMATION_ABOUT_PRODUCT" -> inlineMessage.getVendorProductInfoMessage(buttonQuery);
            case null -> throw new IllegalArgumentException();
            default -> inlineMessage.getErrorMessage(buttonQuery);
        };
    }
}


