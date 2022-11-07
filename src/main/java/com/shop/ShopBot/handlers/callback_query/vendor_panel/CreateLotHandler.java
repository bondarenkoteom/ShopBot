package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
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
