package com.shop.ShopBot.handlers.callback_query.buy_process;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
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
