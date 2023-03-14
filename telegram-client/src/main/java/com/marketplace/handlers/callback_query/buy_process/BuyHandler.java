package com.marketplace.handlers.callback_query.buy_process;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.ButtonText;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Purchase;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.requests.BuyRequest;
import com.marketplace.responses.BuyResponse;
import com.marketplace.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "BUY .*", type = MessageType.CALLBACK_QUERY)
public class BuyHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setUserId(getUserId(update));
        buyRequest.setProductId(Long.valueOf(keys.get("i")));

        BuyResponse buyResponse = httpCoreInterface.buy(buyRequest);

        Payload payload = new Payload(update);
        if (buyResponse.getError()) {
            payload.setSendMethod(SendMethod.SEND_MESSAGE);
            payload.setText("Not enough money");
            bot.process(payload);
            return;
        }

        if (buyResponse.getPurchase().isPresent()) {
            payload.setSendMethod(SendMethod.DELETE);
            bot.process(payload);


            Purchase purchase = buyResponse.getPurchase().get();
            Map<String, String> firstRow = Map.of(
                    "CONFIRM_DELIVERY -i " + purchase.getId(), ButtonText.CONFIRM_DELIVERY,
                    "CHAT -i " + purchase.getSellerId(), ButtonText.CHAT_WITH_SELLER
            );
            Map<String, String> secondRow = Map.of(
                    "OPEN_DISPUTE -i " + purchase.getId(), ButtonText.OPEN_DISPUTE,
                    "CLOSE_DISPUTE -i " + purchase.getId(), ButtonText.CLOSE_DISPUTE
            );
            payload.setSendMethod(SendMethod.SEND_MESSAGE);
            payload.setKeyboard(Buttons.newBuilder()
                    .setButtonsVertical(firstRow)
                    .setButtonsHorizontal(secondRow).build());
            payload.setParseMode(ParseMode.HTML);
            payload.setText("""
                🚀 Purchase succeed!
                
                -&gt; <b>%s</b>
                
                🧾 Now your balance is: $%s
                ---------------------------------------------------------------------------
                Here some instructions to use:
                %s
                
                Your purchase:
                <code>%s</code>
                """.formatted(purchase.getName(), buyResponse.getBalance(), purchase.getInstruction(), purchase.getItem()));
            bot.process(payload);
        }
    }

}
