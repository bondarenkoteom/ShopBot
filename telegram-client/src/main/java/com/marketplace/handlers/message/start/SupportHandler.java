package com.marketplace.handlers.message.start;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.handlers.callback.support.SupportCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "SUPPORT", type = MessageType.INPUT_MESSAGE)
public class SupportHandler extends AbstractBaseHandler {

    @Autowired
    private ApplicationContext context;

    @Override
    public void handle(Update update) {
        returnTriggerValue(update);
        SupportCommandHandler supportCommandHandler = context.getBean(SupportCommandHandler.class);
        update.getMessage().setText("SUPPORT -m SEND_MESSAGE");
        supportCommandHandler.handle(update);
    }
}
