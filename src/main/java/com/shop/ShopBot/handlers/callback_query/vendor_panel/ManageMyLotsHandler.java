package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.ProductStatus;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.utils.Buttons;
import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "MANAGE_LOTS", type = MessageType.CALLBACK_QUERY)
public class ManageMyLotsHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.EDIT_TEXT);
        payload.setText("Your lots list");

        List<Product> products = productService.getAllProducts(update.getCallbackQuery().getFrom().getId());

        Map<String, String> buttons = products.stream()
                .collect(Collectors.toMap(p -> "LOT_" + p.getId() + "#" + SendMethod.SEND_PHOTO, p -> {
                    String status = p.getStatus().equals(ProductStatus.ACTIVE) ?
                            MessageText.LOT_IS_ACTIVE.text() : MessageText.LOT_IS_NOT_ACTIVE.text();
                    return "(%s) %s".formatted(status, p.getProductName());
                }));
        payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).setBackButton("VENDOR_PANEL#" + SendMethod.EDIT_TEXT).build());
        bot.process(payload);
    }
}
