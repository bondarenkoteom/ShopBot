package com.marketplace.handlers.callback.support;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.ButtonText;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
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
