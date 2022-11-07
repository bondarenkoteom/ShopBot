package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.utils.Buttons;
import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Component
@BotCommand(command = "LOT_\\d+#.*", type = MessageType.CALLBACK_QUERY)
public class LotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String productId = update.getCallbackQuery().getData().replace("LOT_", "").replaceAll("#.*", "");
        String method = update.getCallbackQuery().getData().replaceAll("LOT_\\d+#", "");

        Optional<Product> productOptional = productService.getById(Long.valueOf(productId));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.valueOf(method));
            payload.setText(getFormattedTextMessage(product));
            payload.setFileId(product.getImageId());

            Map<String, String> firstRow = product.getStatus().equals(ProductStatus.NOT_ACTIVE) ?
                    Map.of("ACTIVATE_LOT_" + product.getId(), ButtonText.ACTIVATE_LOT.text()) :
                    Map.of("DEACTIVATE_LOT_" + product.getId(), ButtonText.DEACTIVATE_LOT.text());

            Map<String, String> secondRow = Map.of(
                    "DELETE_LOT_" + product.getId(), ButtonText.DELETE_LOT.text(),
                    "EDIT_LOT_" + product.getId(), ButtonText.EDIT_LOT.text()
            );
            payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsHorizontal(firstRow).setButtonsHorizontal(secondRow).build());

            bot.process(payload);
        }
    }

    private String getFormattedTextMessage(Product product) {
        return MessageText.LOT.text().formatted(
                product.getStatus().equals(ProductStatus.NOT_ACTIVE) ?
                        MessageText.LOT_IS_NOT_ACTIVE.text() : MessageText.LOT_IS_ACTIVE.text(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                "0", "1"
        );
    }
}
