package com.marketplace.handlers.callback.byuer_panel.purchases;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.ButtonText;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Purchase;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@BotCommand(command = "PURCHASE .*", type = MessageType.CALLBACK_QUERY)
public class PurchaseHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Purchase> purchaseOptional = httpCoreInterface.purchaseGet(Long.valueOf(keys.get("i")));

        if (purchaseOptional.isPresent()) {
            Purchase purchase = purchaseOptional.get();
            Payload payload = new Payload(update);

            Map<String, String> firstRow = Map.of(
                    "CONFIRM_DELIVERY -i " + purchase.getId(), ButtonText.CONFIRM_DELIVERY,
                    "CHAT -i " + purchase.getSeller().getId(), ButtonText.CHAT_WITH_SELLER
            );
            Map<String, String> secondRow = Map.of(
                    "OPEN_DISPUTE -i " + purchase.getId(), ButtonText.OPEN_DISPUTE,
                    "CLOSE_DISPUTE -i " + purchase.getId(), ButtonText.CLOSE_DISPUTE
            );
            payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
            payload.setKeyboard(Buttons.newBuilder()
                    .setButtonsVertical(firstRow)
                    .setButtonsHorizontal(secondRow).build());
            payload.setParseMode(ParseMode.HTML);
            payload.setText("""
                        🚀 Purchase succeed!
                        
                        -&gt; <b>%s</b>
                        
                        🧾 Price: $%s
                        ---------------------------------------------------------------------------
                        Here some instructions to use:
                        %s
                        
                        Your purchase:
                        <code>%s</code>
                        """.formatted(purchase.getName(), purchase.getPrice(), purchase.getInstruction(), purchase.getItem()));
            bot.process(payload);
        } else {
            log.error("");
        }
        
    }
}
