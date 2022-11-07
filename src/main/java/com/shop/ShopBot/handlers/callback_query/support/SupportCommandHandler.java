package com.shop.ShopBot.handlers.callback_query.support;

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
@BotCommand(command = "SUPPORT#.+", type = MessageType.CALLBACK_QUERY)
public class SupportCommandHandler extends AbstractBaseHandler {


    @Override
    public void handle(Update update) {
        String method = getMethod(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(method));
        payload.setText(MessageText.HELP_MESSAGE.text());

        Map<String, String> buttons = Map.of(
                "HELP_BTC", ButtonText.HELP_BTC.text(),
                "HELP_SELL", ButtonText.HELP_SELL.text(),
                "HELP_BUYER", ButtonText.HELP_BUYER.text()
        );
        payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
