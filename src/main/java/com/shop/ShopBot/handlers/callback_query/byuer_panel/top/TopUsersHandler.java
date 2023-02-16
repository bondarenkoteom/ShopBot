package com.shop.ShopBot.handlers.callback_query.byuer_panel.top;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@BotCommand(command = "TOP_USERS", type = MessageType.CALLBACK_QUERY)
public class TopUsersHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setParseMode(ParseMode.HTML);

        List<User> vendors = userService.findTop25Vendors();
        StringBuilder stringBuilder = new StringBuilder("<b>Top-25 vendors:</b>");
        vendors.forEach(vendor -> {
            stringBuilder.append(String.format("%n%n<b>%s</b>%nRating: %d  Sells: %d", vendor.getUsername(), vendor.getRating(),  vendor.getSells()));
        });

        payload.setText(stringBuilder.toString());
        bot.process(payload);
    }
}
