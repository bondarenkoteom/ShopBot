package com.shop.ShopBot.handlers.callback_query.byuer_panel.disputes;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Dispute;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
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

        bot.process(payload);
    }

    private String getFormattedChatText(List<Dispute> messages, Long superUserId) {
        return messages.stream().map(message -> {
            if (message.getSender().getId().equals(superUserId)) {
                return "you -> \n" + message.getText();
            } else {
                return "<- " + message.getSender().getUsername() + "\n" + message.getText();
            }
        }).collect(Collectors.joining("\n\n"));
    }
}
