package com.shop.ShopBot.handlers.callback_query.byuer_panel.messages;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.User;
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
@BotCommand(command = "BUYER_MESSAGES .*", type = MessageType.CALLBACK_QUERY)
public class BuyerMessagesHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setText("List of chats");

        List<User> users = userService.getChatsUsers(update.getCallbackQuery().getFrom().getId());

        Map<String, String> buttons = users.stream().collect(Collectors.toMap(
                k -> "BUYER_MESSAGE -i %s -p -1 -m %s".formatted(k.getId(), SendMethod.EDIT_TEXT),
                User::getUsername, (a, b) -> a, LinkedHashMap::new
        ));
        payload.setKeyboard(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
