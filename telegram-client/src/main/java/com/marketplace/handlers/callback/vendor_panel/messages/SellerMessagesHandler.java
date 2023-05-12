package com.marketplace.handlers.callback.vendor_panel.messages;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "SELLER_MESSAGES .*", type = MessageType.CALLBACK_QUERY)
public class SellerMessagesHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
        payload.setText("List of chats");

        List<User> users = httpCoreInterface.messagesUsersGet(update.getCallbackQuery().getFrom().getId());

        Map<String, String> buttons = users.stream().collect(Collectors.toMap(
                k -> "SELLER_MESSAGE -i %s -p -1 -m %s".formatted(k.getId(), SendMethod.EDIT_TEXT),
                User::getUsername, (a, b) -> a, LinkedHashMap::new
        ));
        payload.setKeyboard(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
