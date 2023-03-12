package com.marketplace.handlers.callback_query.byuer_panel.disputes;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Purchase;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
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

        List<Purchase> purchases = httpCoreInterface.disputesPurchasesGet(null, update.getCallbackQuery().getFrom().getId());

        Map<String, String> buttons = purchases.stream().collect(Collectors.toMap(
                k -> "BUYER_DISPUTE -i %s".formatted(k.getId()),
                v -> v.getId() + " | " + v.getName(), (a, b) -> a, LinkedHashMap::new
        ));
        payload.setKeyboard(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
