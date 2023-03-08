package com.marketplace.handlers.callback_query.byuer_panel.categories;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.Category;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Keyboard;
import com.marketplace.entity.Payload;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "SEARCH_CATEGORIES", type = MessageType.CALLBACK_QUERY)
public class SearchCategoriesHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {

        Payload payload = new Payload(update);

        payload.setText("Categories");
        payload.setSendMethod(SendMethod.EDIT_TEXT);

        payload.setKeyboard(Keyboard.newBuilder()
                .row()
                .button("SEARCH -p 0 -m SEND_MESSAGE -q '' -c %s".formatted(Category.PS_5_KEYS), "PS5 Keys")
                .button("SEARCH -p 0 -m SEND_MESSAGE -q '' -c %s".formatted(Category.XBOX_KEYS), "Xbox Keys")
                .button("SEARCH -p 0 -m SEND_MESSAGE -q '' -c %s".formatted(Category.ACCOUNTS), "Accounts")
                .row()
                .button("SEARCH -p 0 -m SEND_MESSAGE -q '' -c %s".formatted(Category.PREPAID_CARDS), "Prepaid cards")
                .button("SEARCH -p 0 -m SEND_MESSAGE -q '' -c %s".formatted(Category.VPN), "VPN")
                .button("SEARCH -p 0 -m SEND_MESSAGE -q '' -c %s".formatted(Category.ALL), "All")
                .row()
                .buttonBack("BUYER_PANEL -m %s".formatted(SendMethod.EDIT_TEXT))
                .build()
        );
        bot.process(payload);
    }
}