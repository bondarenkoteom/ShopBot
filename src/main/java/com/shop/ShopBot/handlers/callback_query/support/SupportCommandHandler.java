package com.shop.ShopBot.handlers.callback_query.support;

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
@BotCommand(command = "SUPPORT .*", type = MessageType.CALLBACK_QUERY)
public class SupportCommandHandler extends AbstractBaseHandler {


    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
        payload.setText(MessageText.HELP_MESSAGE.text());

        Map<String, String> buttons = new LinkedHashMap<>();
        buttons.put("HELP_BTC -m EDIT_TEXT", ButtonText.HELP_BTC);
        buttons.put("HELP_SELL -m EDIT_TEXT", ButtonText.HELP_SELL);
        buttons.put("HELP_BUYER -m EDIT_TEXT", ButtonText.HELP_BUYER);
        payload.setKeyboard(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
