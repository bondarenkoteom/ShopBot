package com.shop.ShopBot.handlers.callback_query.buy_process;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.model.Purchase;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Component
@BotCommand(command = "BUY .*", type = MessageType.CALLBACK_QUERY)
public class BuyHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Product> productOptional = productService.getById(Long.valueOf(keys.get("i")));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            User user = userService.getUser(getUserId(update));

            if (product.getPrice() > user.getBalance()) {
                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("Not enough money");
                return;
            }

            String item = productService.pollItem(product.getId());
            if (!item.isEmpty()) {
                userService.incrementSells(product.getOwnerId());

                user.setBalance(BigDecimal.valueOf(user.getBalance() - product.getPrice())
                        .setScale(2, RoundingMode.DOWN).doubleValue());
                user.setPurchases(user.getPurchases() + 1);

                userService.save(user);

                Purchase purchase = new Purchase();
                purchase.setDate(new Date());
                purchase.setName(product.getProductName());
                purchase.setProductId(product.getId());
                purchase.setInstruction(product.getInstruction());
                purchase.setItem(item);
                purchase.setStatus(OrderStatus.IN_PROGRESS);
                purchase.setBuyer(user);
                purchase.setSeller(userService.getUser(product.getOwnerId()));
                purchase.setPrice(product.getPrice());
                purchase.setInstruction(product.getInstruction());
                purchaseService.createOrder(purchase);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.DELETE);
                bot.process(payload);


                Map<String, String> firstRow = Map.of(
                        "CONFIRM_DELIVERY -i " + purchase.getId(), ButtonText.CONFIRM_DELIVERY,
                        "CHAT -i " + purchase.getSeller().getId(), ButtonText.CHAT_WITH_SELLER
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
                        """.formatted(product.getProductName(), user.getBalance(), product.getInstruction(), item));
                bot.process(payload);
            }


        }
    }

}
