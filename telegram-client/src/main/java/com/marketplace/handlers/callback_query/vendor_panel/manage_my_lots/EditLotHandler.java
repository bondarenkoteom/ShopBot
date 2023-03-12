package com.marketplace.handlers.callback_query.vendor_panel.manage_my_lots;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.ProductStatus;
import com.marketplace.constant.SendMethod;
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
@BotCommand(command = "EDIT_LOT .*", type = MessageType.CALLBACK_QUERY)
public class EditLotHandler extends AbstractBaseHandler {

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

            Map<String, String> firstRow = Map.of(
                    "SET_LOT_NAME -i %s" + product.getId(), "Set lot name",
                    "SET_LOT_DESCRIPTION -i %s" + product.getId(), "Set lot description"
            );

            Map<String, String> secondRow = Map.of(
                    "SET_LOT_PRICE -i %s".formatted(product.getId()), "Set lot price",
                    "SET_LOT_IMAGE -i %s".formatted(product.getId()), "Set lot image"
            );

            Map<String, String> thirdRow = Map.of(
                    "SET_LOT_ITEMS -i %s".formatted(product.getId()), "Set lot items"
            );

            Map<String, String> fourthRow = Map.of(
                    "SET_LOT_INSTRUCTION -i %s".formatted(product.getId()), "Set lot items"
            );

            payload.setKeyboard(Buttons.newBuilder()
                    .setButtonsHorizontal(firstRow)
                    .setButtonsHorizontal(secondRow)
                    .setButtonsHorizontal(thirdRow)
                    .setButtonsHorizontal(fourthRow)
                    .setGoBackButton("LOT -i %s -m %s".formatted(product.getId(), SendMethod.EDIT_CAPTION))
                    .build()
            );

            product.setStatus(ProductStatus.ACTIVE);
            httpCoreInterface.productUpdate(product);
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
