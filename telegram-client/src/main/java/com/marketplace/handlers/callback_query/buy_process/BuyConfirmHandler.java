package com.marketplace.handlers.callback_query.buy_process;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Product;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Keyboard;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@BotCommand(command = "BUY_CONFIRM .*", type = MessageType.CALLBACK_QUERY)
public class BuyConfirmHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Long productId = Long.valueOf(keys.get("i"));

        Optional<Product> productOptional = httpCoreInterface.productGet(productId);

        if (productOptional.isPresent()) {

            Payload payload = new Payload(update);
            payload.setText("Are you sure that you want to by this product?");

            if (SendMethod.valueOf(keys.get("m")).equals(SendMethod.DELETE)) {
                payload.setSendMethod(SendMethod.DELETE);
                bot.process(payload);
            } else {
                payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
                payload.setKeyboard(Keyboard.newBuilder()
                        .row()
                        .button("BUY -i %s".formatted(productOptional.get().getId()), "Yes")
                        .button("BUY_CONFIRM -i %s -m %s".formatted(productId, SendMethod.DELETE), "No")
                        .build()
                );
                bot.process(payload);
            }
        }

    }
}
