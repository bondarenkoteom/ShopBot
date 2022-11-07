package com.shop.ShopBot.handlers.input_message.main_buttons;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.handlers.callback_query.wallet.WalletCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "WALLET", type = MessageType.INPUT_MESSAGE)
public class WalletHandler extends AbstractBaseHandler {

    @Autowired
    private ApplicationContext context;

    @Override
    public void handle(Update update) {
        returnTriggerValue(update);
        WalletCommandHandler walletCommandHandler = context.getBean(WalletCommandHandler.class);
        update.getMessage().setText("WALLET#SEND_MESSAGE");
        walletCommandHandler.handle(update);
    }
}
