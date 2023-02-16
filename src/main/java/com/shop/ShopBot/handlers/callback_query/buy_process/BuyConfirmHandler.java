package com.shop.ShopBot.handlers.callback_query.buy_process;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Keyboard;
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

        Optional<Product> productOptional = productService.getById(productId);

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
