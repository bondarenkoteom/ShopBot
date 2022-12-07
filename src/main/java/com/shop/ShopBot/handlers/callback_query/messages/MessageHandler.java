package com.shop.ShopBot.handlers.callback_query.messages;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Message;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "MESSAGE .*", type = MessageType.CALLBACK_QUERY)
public class MessageHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Long userId = Long.parseLong(keys.get("i"));
        Long superUserId = update.getCallbackQuery().getFrom().getId();

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);

        List<Message> messages = messageService.getChatMessages(superUserId, userId);

        String chatText = getFormattedChatText(messages, superUserId);
        payload.setText(chatText);

        bot.process(payload);
    }

    private String getFormattedChatText(List<Message> messages, Long superUserId) {
        return messages.stream().map(message -> {
            if (message.getSender().getId().equals(superUserId)) {
                return "you -> \n" + message.getText();
            } else {
                return "<- " + message.getSender().getUsername() + "\n" + message.getText();
            }
        }).collect(Collectors.joining("\n\n"));
    }
}
