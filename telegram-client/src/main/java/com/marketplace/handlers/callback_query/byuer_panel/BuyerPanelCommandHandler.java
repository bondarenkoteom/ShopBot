package com.marketplace.handlers.callback_query.byuer_panel;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.ButtonText;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Keyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "BUYER_PANEL .*", type = MessageType.CALLBACK_QUERY)
public class BuyerPanelCommandHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        returnTriggerValue(update);

        Keys keys = getKeys(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
        payload.setText(MessageText.CHOOSE_OPTION.text());

        payload.setKeyboard(Keyboard.newBuilder()
                .row()
                .button("PURCHASES -p 0", ButtonText.PURCHASES)
                .row()
                .button("BUYER_MESSAGES -m %s".formatted(SendMethod.SEND_MESSAGE), ButtonText.MESSAGES)
                .button("BUYER_DISPUTES", ButtonText.DISPUTES)
                .row()
                .button("TOP_USERS", ButtonText.TOP_USERS)
                .row()
                .button("SEARCH_CATEGORIES", ButtonText.CATEGORIES)
                .build()
        );
        bot.process(payload);
    }
}
