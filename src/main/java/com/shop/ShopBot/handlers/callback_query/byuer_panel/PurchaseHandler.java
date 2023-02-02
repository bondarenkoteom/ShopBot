package com.shop.ShopBot.handlers.callback_query.byuer_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.model.Purchase;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import com.shop.ShopBot.utils.SimplePagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
                    "CONFIRM -i " + purchase.getId(), ButtonText.CONFIRM_DELIVERY.text(),
                    "CHAT -i " + purchase.getId(), ButtonText.CHAT_WITH_SELLER.text()
            );
            Map<String, String> secondRow = Map.of(
                    "OPEN_DISPUTE -i " + purchase.getId(), ButtonText.OPEN_DISPUTE.text(),
                    "CLOSE_DISPUTE -i " + purchase.getId(), ButtonText.CLOSE_DISPUTE.text()
            );
            payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
            payload.setKeyboardMarkup(Buttons.newBuilder()
                    .setButtonsVertical(firstRow)
                    .setButtonsHorizontal(secondRow).build());
            payload.setParseMode(ParseMode.HTML);
            payload.setText("""
                        🚀 Purchase succeed!
                        
                        -&gt; <b>%s</b>
                        
                        🧾 Price: $%s
                        ---------------------------------------------------------------------------
                        Here some instructions to use:
                        1. Open http://google.com
                        2. Enter this code to input box
                        3. Enjoy
                        
                        Your purchase:
                        <code>%s</code>
                        """.formatted(purchase.getName(), purchase.getPrice(), purchase.getItem()));
            bot.process(payload);
        }
        
    }
}
