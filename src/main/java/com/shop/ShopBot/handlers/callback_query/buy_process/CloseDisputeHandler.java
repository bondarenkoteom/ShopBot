package com.shop.ShopBot.handlers.callback_query.buy_process;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "CLOSE_DISPUTE .*", type = MessageType.CALLBACK_QUERY)
public class CloseDisputeHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {

    }
}
