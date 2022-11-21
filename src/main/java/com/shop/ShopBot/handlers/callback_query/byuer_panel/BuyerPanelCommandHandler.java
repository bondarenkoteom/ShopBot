package com.shop.ShopBot.handlers.callback_query.byuer_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "BUYER_PANEL .*", type = MessageType.CALLBACK_QUERY)
public class BuyerPanelCommandHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        returnTriggerValue(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setText(MessageText.CHOOSE_OPTION.text());

        Map<String, String> buttons = Map.of(
                "PURCHASES", ButtonText.PURCHASES.text()
        );
        payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
