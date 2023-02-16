package com.shop.ShopBot.handlers.callback_query.vendor_panel.manage_my_lots;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.ProductStatus;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
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

        Optional<Product> productOptional = productService.getById(Long.valueOf(keys.get("i")));

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
