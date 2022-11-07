package com.shop.ShopBot.handlers.callback_query.vendor_panel;

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
@BotCommand(command = "DEACTIVATE_LOT_\\d+", type = MessageType.CALLBACK_QUERY)
public class DeactivateLotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String productId = update.getCallbackQuery().getData().replace("DEACTIVATE_LOT_", "");
        Optional<Product> productOptional = productService.getById(Long.valueOf(productId));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.EDIT_CAPTION);
            payload.setText(getFormattedTextMessage(product));
            payload.setFileId(product.getImageId());

            Map<String, String> firstRow = Map.of("ACTIVATE_LOT_" + product.getId(), ButtonText.ACTIVATE_LOT.text());

            Map<String, String> secondRow = Map.of(
                    "DELETE_LOT_" + product.getId(), ButtonText.DELETE_LOT.text(),
                    "EDIT_LOT_" + product.getId(), ButtonText.EDIT_LOT.text()
            );
            payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsHorizontal(firstRow).setButtonsHorizontal(secondRow).build());

            product.setStatus(ProductStatus.NOT_ACTIVE);
            productService.save(product);
            bot.process(payload);
        }
    }

    private String getFormattedTextMessage(Product product) {
        return MessageText.LOT.text().formatted(
                MessageText.LOT_IS_NOT_ACTIVE.text(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                "0", "1"
        );
    }
}
