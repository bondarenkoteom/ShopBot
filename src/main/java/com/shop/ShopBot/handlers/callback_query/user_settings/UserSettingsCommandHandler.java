package com.shop.ShopBot.handlers.callback_query.user_settings;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "USER_SETTINGS#.+", type = MessageType.CALLBACK_QUERY)
public class UserSettingsCommandHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String method = getMethod(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(method));
        payload.setText(MessageText.SETTINGS_DEFAULT_MESSAGE.text());

        Map<String, String> buttons = Map.of(
                "SET_USERNAME", ButtonText.SET_NAME.text(),
                "USER_INFO", ButtonText.USER_INFO.text()
        );
        payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
