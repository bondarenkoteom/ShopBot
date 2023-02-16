package com.shop.ShopBot.handlers.callback_query.byuer_panel.purchases;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.database.model.Purchase;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Component
@BotCommand(command = "PURCHASE .*", type = MessageType.CALLBACK_QUERY)
public class PurchaseHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Purchase> purchaseOptional = purchaseService.getById(Long.valueOf(keys.get("i")));

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
        }
        
    }
}
