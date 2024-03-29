package com.marketplace.handlers.callback.vendor_panel.manage_my_lots;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.*;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Product;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Component
@BotCommand(command = "ACTIVATE_LOT .*", type = MessageType.CALLBACK_QUERY)
public class ActivateLotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Product> productOptional = httpCoreInterface.productGet(Long.valueOf(keys.get("i")));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.EDIT_CAPTION);
            payload.setText(getFormattedTextMessage(product));
            payload.setFileId(product.getImageId());

            Map<String, String> firstRow = Map.of("DEACTIVATE_LOT -i %s".formatted(product.getId()), ButtonText.DEACTIVATE_LOT);
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

            product.setStatus(ProductStatus.ACTIVE);
            httpCoreInterface.productUpdate(product);
            bot.process(payload);
        }
    }

    private String getFormattedTextMessage(Product product) {
        return MessageText.LOT.text().formatted(
                MessageText.LOT_IS_ACTIVE.text(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                "0", "1"
        );
    }
}
