package com.marketplace.handlers.callback.vendor_panel.create_new_lot;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.Trigger;
import com.marketplace.constant.MessageType;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "CREATE_LOT", type = MessageType.CALLBACK_QUERY)
public class CreateLotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);

        payload.setText(MessageText.CREATE_NEW_LOT.text());
        bot.process(payload);

        payload.setText("Please enter image of your lot. Use image with size less than 5mb.");
        bot.process(payload);

        setTriggerValue(update, Trigger.NEW_PRODUCT_IMAGE);
    }
}
