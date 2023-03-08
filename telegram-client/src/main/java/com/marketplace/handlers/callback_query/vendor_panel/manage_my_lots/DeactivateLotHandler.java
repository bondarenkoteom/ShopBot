package com.marketplace.handlers.callback_query.vendor_panel.manage_my_lots;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.*;
import com.marketplace.database.model.Product;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
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

            Map<String, String> firstRow = Map.of("ACTIVATE_LOT -i %s".formatted(product.getId()), ButtonText.ACTIVATE_LOT);
            Map<String, String> secondRow = Map.of("GET_LOT_ITEMS -i %s".formatted(product.getId()),
                    ButtonText.GET_LOT_ITEMS.formatted(product.getItems() == null ? 0 : product.getItems().length));

            Map<String, String> thirdRow = Map.of(
                    "DELETE_LOT -i %s".formatted(product.getId()), ButtonText.DELETE_LOT,
                    "EDIT_LOT -i %s".formatted(product.getId()), ButtonText.EDIT_LOT
            );
            payload.setKeyboard(Buttons.newBuilder()
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
