package com.marketplace.handlers.callback_query.byuer_panel.disputes;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.database.model.Dispute;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.DateFormat;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
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

        List<Dispute> messages = disputeService.getDisputeMessages(purchaseId);

        String chatText = getFormattedChatText(messages, superUserId);
        if (chatText.isEmpty()) chatText = "No messages yet";
        payload.setText(chatText);
        payload.setParseMode(ParseMode.HTML);

        bot.process(payload);
    }

    private String getFormattedChatText(List<Dispute> messages, Long superUserId) {
        return messages.stream().map(message -> {
            if (message.getSender().getId().equals(superUserId)) {
                return String.format("<b># You</b> [%s]%n", DateFormat.format(message.getDate())) + message.getText();
            } else {
                return String.format("<b># %s</b> [%s]%n", message.getSender().getUsername(), DateFormat.format(message.getDate())) + message.getText();
            }
        }).collect(Collectors.joining("\n\n"));
    }
}
