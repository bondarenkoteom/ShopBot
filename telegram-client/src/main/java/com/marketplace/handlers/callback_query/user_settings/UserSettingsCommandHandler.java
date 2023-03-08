package com.marketplace.handlers.callback_query.user_settings;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import com.marketplace.constant.ButtonText;
import com.marketplace.database.model.User;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

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

            payload.setParseMode(ParseMode.HTML);
            payload.setText(MessageText.PERSONAL_INFO.text().formatted(
                    user.getUsername(),
                    user.getId(),
                    user.getRating(),
                    user.getSells(),
                    user.getPurchases(),
                    10,                 //Disputes win
                    0                   //Disputes lose
            ));

            payload.setKeyboard(Buttons.newBuilder()
                    .setButton("SET_USERNAME", ButtonText.SET_NAME)
                    .build());
            bot.process(payload);
        }

    }
}
