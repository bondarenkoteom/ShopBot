package com.marketplace.handlers.message.start;

import com.marketplace.annotations.BotCommand;
import com.marketplace.handlers.callback.wallet.WalletCommandHandler;
import com.marketplace.constant.MessageType;
import com.marketplace.handlers.AbstractBaseHandler;
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
        update.getMessage().setText("WALLET -m SEND_MESSAGE");
        walletCommandHandler.handle(update);
    }
}
