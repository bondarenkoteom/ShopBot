package com.shop.ShopBot.handlers.callback_query.search;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Component
@BotCommand(command = "PRODUCT_\\d+#.*", type = MessageType.CALLBACK_QUERY)
public class ProductHandler extends AbstractBaseHandler {
    @Override
    public void handle(Update update) {
        String productId = update.getCallbackQuery().getData().replace("PRODUCT_", "").replaceAll("#.*", "");
        String method = update.getCallbackQuery().getData().replaceAll("PRODUCT_\\d+#", "");

        Optional<Product> productOptional = productService.getById(Long.valueOf(productId));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Payload payload = new Payload(update);

            payload.setSendMethod(SendMethod.DELETE);
            bot.process(payload);

            payload.setSendMethod(SendMethod.valueOf(method));
            payload.setText(getFormattedTextMessage(product));
            payload.setFileId(product.getImageId());

            Map<String, String> button = Map.of("ACTIVATE_LOT_" + product.getId(), "Buy");

            Map<String, String> pagination = Map.of(
                    "PREVIOUS_", "« Previous",
                    "NEXT_", "Next »"
            );
            payload.setKeyboardMarkup(Buttons.newBuilder()
                    .setButtonsHorizontal(button)
                    .setButtonsHorizontal(pagination)
                    .setBackButton("SEARCH_" + product.getId() + "#" + SendMethod.SEND_MESSAGE)
                    .build()
            );

            bot.process(payload);
        }
    }

    private String getFormattedTextMessage(Product product) {
        return MessageText.PRODUCT.text().formatted(
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                "0", "1"
        );
    }
}
