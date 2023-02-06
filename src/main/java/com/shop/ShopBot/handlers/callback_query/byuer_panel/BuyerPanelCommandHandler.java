package com.shop.ShopBot.handlers.callback_query.byuer_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@BotCommand(command = "BUYER_PANEL .*", type = MessageType.CALLBACK_QUERY)
public class BuyerPanelCommandHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        returnTriggerValue(update);

        Keys keys = getKeys(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
        payload.setText(MessageText.CHOOSE_OPTION.text());

        Map<String, String> firstRow = Map.of(
                "PURCHASES -p 0", ButtonText.PURCHASES.text()
        );

        Map<String, String> secondRow = new LinkedHashMap<>();
        secondRow.put("BUYER_MESSAGES -m %s".formatted(SendMethod.SEND_MESSAGE), ButtonText.MESSAGES.text());
        secondRow.put("BUYER_DISPUTES", ButtonText.DISPUTES.text());

        payload.setKeyboardMarkup(Buttons.newBuilder()
                .setButtonsVertical(firstRow)
                .setButtonsHorizontal(secondRow)
                .build());
        bot.process(payload);
    }
}
