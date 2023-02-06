package com.shop.ShopBot.handlers.callback_query.byuer_panel.disputes;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Purchase;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "BUYER_DISPUTES", type = MessageType.CALLBACK_QUERY)
public class BuyerDisputesHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setText("List of disputes");

        List<Purchase> purchases = purchaseService.getBuyerChatsPurchases(update.getCallbackQuery().getFrom().getId());

        Map<String, String> buttons = purchases.stream().collect(Collectors.toMap(
                k -> "BUYER_DISPUTE -i %s".formatted(k.getId()),
                v -> v.getId() + " | " + v.getName(), (a, b) -> a, LinkedHashMap::new
        ));
        payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
