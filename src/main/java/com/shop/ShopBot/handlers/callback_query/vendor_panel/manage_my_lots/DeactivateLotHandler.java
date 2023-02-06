package com.shop.ShopBot.handlers.callback_query.vendor_panel.manage_my_lots;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.*;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Component
@BotCommand(command = "DEACTIVATE_LOT .*", type = MessageType.CALLBACK_QUERY)
public class DeactivateLotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Product> productOptional = productService.getById(Long.valueOf(keys.get("i")));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.EDIT_CAPTION);
            payload.setText(getFormattedTextMessage(product));
            payload.setFileId(product.getImageId());

            Map<String, String> firstRow = Map.of("ACTIVATE_LOT -i %s".formatted(product.getId()), ButtonText.ACTIVATE_LOT.text());
            Map<String, String> secondRow = Map.of("GET_LOT_ITEMS -i %s".formatted(product.getId()),
                    ButtonText.GET_LOT_ITEMS.text().formatted(product.getItems() == null ? 0 : product.getItems().length));

            Map<String, String> thirdRow = Map.of(
                    "DELETE_LOT -i %s".formatted(product.getId()), ButtonText.DELETE_LOT.text(),
                    "EDIT_LOT -i %s".formatted(product.getId()), ButtonText.EDIT_LOT.text()
            );
            payload.setKeyboardMarkup(Buttons.newBuilder()
                    .setButtonsHorizontal(firstRow)
                    .setButtonsHorizontal(secondRow)
                    .setButtonsHorizontal(thirdRow).build());

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
