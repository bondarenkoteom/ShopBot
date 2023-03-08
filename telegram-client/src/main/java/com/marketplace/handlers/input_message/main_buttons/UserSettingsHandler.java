package com.marketplace.handlers.input_message.main_buttons;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.handlers.callback_query.user_settings.UserSettingsCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "USER SETTINGS", type = MessageType.INPUT_MESSAGE)
public class UserSettingsHandler extends AbstractBaseHandler {

    @Autowired
    private ApplicationContext context;

    @Override
    public void handle(Update update) {
        returnTriggerValue(update);
        UserSettingsCommandHandler userSettingsCommandHandler = context.getBean(UserSettingsCommandHandler.class);
        update.getMessage().setText("USER_SETTINGS -m SEND_MESSAGE");
        userSettingsCommandHandler.handle(update);
    }
}
