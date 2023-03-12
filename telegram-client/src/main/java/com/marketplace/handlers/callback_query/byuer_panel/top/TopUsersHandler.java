package com.marketplace.handlers.callback_query.byuer_panel.top;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
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

        List<User> vendors = httpCoreInterface.topUsers();
        StringBuilder stringBuilder = new StringBuilder("<b>Top-25 vendors:</b>");
        vendors.forEach(vendor -> {
            stringBuilder.append(String.format("%n%n<b>%s</b>%nRating: %d  Sells: %d", vendor.getUsername(), vendor.getRating(),  vendor.getSells()));
        });

        payload.setText(stringBuilder.toString());
        bot.process(payload);
    }
}
