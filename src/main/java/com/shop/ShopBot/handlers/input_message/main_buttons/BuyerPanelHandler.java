package com.shop.ShopBot.handlers.input_message.main_buttons;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.handlers.callback_query.byuer_panel.BuyerPanelCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "BUYER PANEL", type = MessageType.INPUT_MESSAGE)
public class BuyerPanelHandler extends AbstractBaseHandler {

    @Autowired
    private ApplicationContext context;

    @Override
    public void handle(Update update) {
        returnTriggerValue(update);
        BuyerPanelCommandHandler buyerPanelCommandHandler = context.getBean(BuyerPanelCommandHandler.class);
        update.getMessage().setText("BUYER_PANEL -m SEND_MESSAGE");
        buyerPanelCommandHandler.handle(update);
    }
}
