package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.ProductStatus;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.Optional;

@Component
@BotCommand(command = "EDIT_LOT_\\d+", type = MessageType.CALLBACK_QUERY)
public class EditLotHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String productId = update.getCallbackQuery().getData().replace("EDIT_LOT_", "");
        Optional<Product> productOptional = productService.getById(Long.valueOf(productId));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.EDIT_CAPTION);
            payload.setText(getFormattedTextMessage(product));
            payload.setFileId(product.getImageId());

            Map<String, String> firstRow = Map.of(
                    "SET_LOT_NAME_" + product.getId(), "Set lot name",
                    "SET_LOT_DESCRIPTION_" + product.getId(), "Set lot description"
            );

            Map<String, String> secondRow = Map.of(
                    "SET_LOT_PRICE_" + product.getId(), "Set lot price",
                    "SET_LOT_IMAGE_" + product.getId(), "Set lot image"
            );

            Map<String, String> thirdRow = Map.of(
                    "SET_LOT_ITEMS_" + product.getId(), "Set lot items"
            );

            payload.setKeyboardMarkup(Buttons.newBuilder()
                    .setButtonsHorizontal(firstRow)
                    .setButtonsHorizontal(secondRow)
                    .setButtonsHorizontal(thirdRow)
                    .setBackButton("LOT_" + product.getId() + "#" + SendMethod.EDIT_CAPTION)
                    .build()
            );

            product.setStatus(ProductStatus.ACTIVE);
            productService.save(product);
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
