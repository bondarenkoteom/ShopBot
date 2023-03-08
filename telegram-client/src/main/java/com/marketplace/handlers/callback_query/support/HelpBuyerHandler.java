package com.marketplace.handlers.callback_query.support;

import com.marketplace.constant.SendMethod;
import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.MessageType;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
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
