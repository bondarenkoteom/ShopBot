package com.shop.ShopBot.handlers.input_message.main_buttons;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.handlers.callback_query.search.SearchHandler;
import com.shop.ShopBot.handlers.callback_query.support.SupportCommandHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

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
