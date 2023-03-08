package com.marketplace.handlers.callback_query.byuer_panel.purchases;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.OrderStatus;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.MessageType;
import com.marketplace.database.model.Purchase;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import com.marketplace.utils.SimplePagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "PURCHASES .*", type = MessageType.CALLBACK_QUERY)
public class PurchasesHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        int pageNumber = Integer.parseInt(keys.get("p"));
        int elementsPerPage = 10;

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.EDIT_TEXT);
        payload.setText("Your last deals list");

        Page<Purchase> purchases = purchaseService.getAllPurchases(update.getCallbackQuery().getFrom().getId(), pageable);

        Map<String, String> buttons = purchases.stream()
                .collect(Collectors.toMap(p -> "PURCHASE -i %s -m %s".formatted(p.getId(), SendMethod.SEND_MESSAGE), p -> {
                    OrderStatus status = p.getStatus();
                    return switch (status) {
                        case IN_PROGRESS -> "🕑 %s (%s)".formatted(p.getName(), p.getDate());
                        case DECLINED -> "❌ %s (%s)".formatted(p.getName(), p.getDate());
                        case DISPUTE -> "😡 %s (%s)".formatted(p.getName(), p.getDate());
                        case default -> "✅ %s (%s)".formatted(p.getName(), p.getDate());
                    };
                }));
        payload.setKeyboard(Buttons.newBuilder()
                .setButtonsVertical(buttons)
                .setButtonsHorizontal(SimplePagination.twoButtonsPagination(purchases, "PURCHASES", "-m EDIT_TEXT"))
                .setGoBackButton("BUYER_PANEL -m %s".formatted(SendMethod.EDIT_TEXT)).build());
        bot.process(payload);
        
    }
}
