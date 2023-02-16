package com.shop.ShopBot.handlers.callback_query.byuer_panel.categories;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.Category;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.handlers.callback_query.search.SearchHandler;
import com.shop.ShopBot.utils.Keyboard;
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