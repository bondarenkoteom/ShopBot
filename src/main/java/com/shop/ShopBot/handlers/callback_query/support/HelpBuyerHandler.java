package com.shop.ShopBot.handlers.callback_query.support;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "HELP_BUYER .*", type = MessageType.CALLBACK_QUERY)
public class HelpBuyerHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);
        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(keys.get("m")));

        payload.setText(MessageText.BUYERS_FEATURES.text());
        payload.setKeyboard(Buttons.newBuilder().setGoBackButton("SUPPORT -m EDIT_TEXT").build());
        bot.process(payload);
    }
}
