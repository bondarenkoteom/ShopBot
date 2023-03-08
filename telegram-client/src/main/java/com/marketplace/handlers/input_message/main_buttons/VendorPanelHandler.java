package com.marketplace.handlers.input_message.main_buttons;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.handlers.callback_query.vendor_panel.VendorPanelCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "VENDOR PANEL", type = MessageType.INPUT_MESSAGE)
public class VendorPanelHandler extends AbstractBaseHandler {

    @Autowired
    private ApplicationContext context;

    @Override
    public void handle(Update update) {
        returnTriggerValue(update);
        VendorPanelCommandHandler vendorPanelCommandHandler = context.getBean(VendorPanelCommandHandler.class);
        update.getMessage().setText("VENDOR_PANEL -m SEND_MESSAGE");
        vendorPanelCommandHandler.handle(update);
    }
}
