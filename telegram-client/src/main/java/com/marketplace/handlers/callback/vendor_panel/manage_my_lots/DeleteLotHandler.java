package com.marketplace.handlers.callback.vendor_panel.manage_my_lots;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "DELETE_LOT .*", type = MessageType.CALLBACK_QUERY)
public class DeleteLotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.DELETE);
        bot.process(payload);

        httpCoreInterface.productDelete(Long.valueOf(keys.get("i")));

        payload.setText("Your lot is completely deleted.");
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        bot.process(payload);
    }
}
