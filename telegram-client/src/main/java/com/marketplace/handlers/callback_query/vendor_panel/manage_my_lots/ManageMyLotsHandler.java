package com.marketplace.handlers.callback_query.vendor_panel.manage_my_lots;

import com.marketplace.constant.MessageText;
import com.marketplace.constant.ProductStatus;
import com.marketplace.constant.SendMethod;
import com.marketplace.database.model.Product;
import com.marketplace.entity.Keys;
import com.marketplace.utils.Buttons;
import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.SimplePagination;
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
