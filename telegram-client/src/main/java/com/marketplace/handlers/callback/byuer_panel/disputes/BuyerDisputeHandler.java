package com.marketplace.handlers.callback.byuer_panel.disputes;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.DisputeMessage;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "BUYER_DISPUTE .*", type = MessageType.CALLBACK_QUERY)
public class BuyerDisputeHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Long purchaseId = Long.parseLong(keys.get("i"));

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);

        List<DisputeMessage> messages = httpCoreInterface.disputesGet(purchaseId);

        Optional<User> optionalUser = httpCoreInterface.userGet(getUserId(update), null);

        if (optionalUser.isPresent() && checkUser(messages, optionalUser.get())) {

            String chatText = getFormattedChatText(messages);
            if (chatText.isEmpty()) chatText = "No messages yet";
            payload.setText(chatText);
            payload.setParseMode(ParseMode.HTML);

            bot.process(payload);
        }
    }

    private String getFormattedChatText(List<DisputeMessage> messages) {
        return messages.stream().map(message ->
            String.format("<b># %s</b> [%s]%n", message.getSender(), message.getDate().toString()) + message.getMessage()
        ).collect(Collectors.joining("\n\n"));
    }

    private boolean checkUser(List<DisputeMessage> messages,User user) {
        return messages.stream().anyMatch(c -> c.getSender().equals(user.getUsername()) || c.getReceiver().equals(user.getUsername()));
    }
}
