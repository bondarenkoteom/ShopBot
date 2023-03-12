package com.marketplace.handlers.callback_query.buy_process;

import com.marketplace.annotations.BotCommand;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.constant.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "CONFIRM_DELIVERY .*", type = MessageType.CALLBACK_QUERY)
public class ConfirmDeliveryHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        long purchaseId = Long.parseLong(getKeys(update).get("i"));
        httpCoreInterface.statusUpdate(purchaseId, OrderStatus.CONFIRMED);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setText("""
                Order was successfully confirmed!
                """);
        bot.process(payload);

    }
}
