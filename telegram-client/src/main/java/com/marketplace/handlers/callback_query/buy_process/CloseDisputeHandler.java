package com.marketplace.handlers.callback_query.buy_process;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "CLOSE_DISPUTE .*", type = MessageType.CALLBACK_QUERY)
public class CloseDisputeHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        long purchaseId = Long.parseLong(getKeys(update).get("i"));
        purchaseService.confirmDelivery(purchaseId);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setText("""
                Dispute closed
                """);
        bot.process(payload);
    }
}
