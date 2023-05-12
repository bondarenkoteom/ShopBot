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
        Long superUserId = update.getCallbackQuery().getFrom().getId();

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);

        List<DisputeMessage> messages = httpCoreInterface.disputesGet(purchaseId);

        Optional<User> optionalUser = httpCoreInterface.userGet(superUserId, null);
        if (optionalUser.isPresent()) {
            String chatText = getFormattedChatText(messages, optionalUser.get(), superUserId);
            if (chatText.isEmpty()) chatText = "No messages yet";
            payload.setText(chatText);
            payload.setParseMode(ParseMode.HTML);

            bot.process(payload);
        }
    }

    private String getFormattedChatText(List<DisputeMessage> messages, User user, Long superUserId) {
        return messages.stream().map(message -> {
            if (user.getId().equals(superUserId)) {
                return String.format("<b># You</b> [%s]%n", message.getDate().toString()) + message.getMessage();
            } else {
                return String.format("<b># %s</b> [%s]%n", user.getUsername(), message.getDate().toString()) + message.getMessage();
            }
        }).collect(Collectors.joining("\n\n"));
    }
}
