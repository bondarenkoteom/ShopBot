package com.shop.ShopBot.handlers.callback_query.user_settings;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "USER_SETTINGS .*", type = MessageType.CALLBACK_QUERY)
public class UserSettingsCommandHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        User user = userService.getUser(getUserId(update));
        if (user != null) {
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.valueOf(keys.get("m")));

            Integer rating = user.getRating() == null ? 0 : user.getRating();

            payload.setParseMode(ParseMode.HTML);
            payload.setText(MessageText.PERSONAL_INFO.text().formatted(
                    user.getUsername(),
                    user.getId(),       //User ID
                    user.getUsername(), //User link
                    rating,             //Rating
                    0,                  //Sells
                    1,                  //Purchases
                    10,                 //Disputes win
                    0                   //Disputes lose
            ));

            payload.setKeyboardMarkup(Buttons.newBuilder()
                    .setButton("SET_USERNAME", ButtonText.SET_NAME.text())
                    .build());
            bot.process(payload);
        }

    }
}
