package com.shop.ShopBot.handlers.callback_query.vendor_panel.manage_my_lots;

import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.ProductStatus;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.utils.Buttons;
import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.SimplePagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "MANAGE_LOTS .*", type = MessageType.CALLBACK_QUERY)
public class ManageMyLotsHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        int pageNumber = Integer.parseInt(keys.get("p"));
        int elementsPerPage = 10;

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.EDIT_TEXT);
        payload.setText("Your lots list");

        Page<Product> products = productService.getAllProducts(update.getCallbackQuery().getFrom().getId(), pageable);

        Map<String, String> buttons = products.stream()
                .collect(Collectors.toMap(p -> "LOT -i %s -m %s".formatted(p.getId(), SendMethod.SEND_PHOTO), p -> {
                    String status = p.getStatus().equals(ProductStatus.ACTIVE) ?
                            MessageText.LOT_IS_ACTIVE.text() : MessageText.LOT_IS_NOT_ACTIVE.text();
                    return "(%s) %s".formatted(status, p.getProductName());
                }));
        payload.setKeyboard(Buttons.newBuilder()
                .setButtonsVertical(buttons)
                .setButtonsHorizontal(SimplePagination.twoButtonsPagination(products, "MANAGE_LOTS", "-m EDIT_TEXT"))
                .setGoBackButton("VENDOR_PANEL -m %s".formatted(SendMethod.EDIT_TEXT)).build());
        bot.process(payload);
    }
}
