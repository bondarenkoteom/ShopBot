package com.marketplace.handlers.callback.user_settings;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.Trigger;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "SET_USERNAME", type = MessageType.CALLBACK_QUERY)
public class SetUsernameHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        setTriggerValue(update, Trigger.USERNAME);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);

        payload.setText("Enter username");
        bot.process(payload);
    }
}