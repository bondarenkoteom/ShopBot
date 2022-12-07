package com.shop.ShopBot.handlers.callback_query.messages;

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
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "MESSAGES", type = MessageType.CALLBACK_QUERY)
public class MessagesHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setText("List of chats");

        List<User> users = userService.getChatsUsers(update.getCallbackQuery().getFrom().getId());

        Map<String, String> buttons = users.stream().collect(Collectors.toMap(
                k -> "MESSAGE -i %s".formatted(k.getId()),
                User::getUsername, (a, b) -> a, LinkedHashMap::new
        ));
        payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
