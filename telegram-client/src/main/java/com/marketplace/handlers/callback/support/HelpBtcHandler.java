package com.marketplace.handlers.callback.support;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "HELP_BTC .*", type = MessageType.CALLBACK_QUERY)
public class HelpBtcHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);
        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(keys.get("m")));

        payload.setText(MessageText.WHAT_IS_BTC.text());
        payload.setKeyboard(Buttons.newBuilder().setNextPageButton("HELP_SELL -m EDIT_TEXT").build());
        bot.process(payload);
    }
}
