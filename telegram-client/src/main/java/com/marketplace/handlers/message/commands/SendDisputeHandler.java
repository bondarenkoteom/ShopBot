package com.marketplace.handlers.message.commands;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.*;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.requests.DisputeRequest;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@BotCommand(command = "/DISPUTE .*", type = MessageType.INPUT_MESSAGE)
public class SendDisputeHandler extends AbstractBaseHandler {

    @Override
    protected void handle(Update update) {
        CommandParts commandParts = new CommandParts(update);
        if (!commandParts.getMessage().isEmpty() && commandParts.getOrder() != null) {

            Optional<Purchase> purchaseOptional = httpCoreInterface.purchaseGet(commandParts.getOrder());

            if (purchaseOptional.isPresent()) {
                Purchase purchase = purchaseOptional.get();
                Long userId = getUserId(update);
                User sender = getSender(purchase, userId);
                User receiver = getReceiver(purchase, userId);

                DisputeRequest dispute = new DisputeRequest(
                        commandParts.getMessage(), sender, receiver, purchase.getId(),
                        getFormattedName(purchase), getFormattedDescription(purchase));

                httpCoreInterface.disputeCreate(dispute);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("🚀 The dispute message was sent");
                bot.process(payload);

                Payload notification = new Payload();

                //todo поменять на получателя
                notification.setChatId(sender.getId().toString());
                notification.setSendMethod(SendMethod.SEND_MESSAGE);
                notification.setText("🚀 You got new message. Check disputes");
                bot.process(notification);
            }

        }
    }

    @Data
    private static class CommandParts {
        private Long order;
        private String message;

        CommandParts(Update update) {
            String query = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
            Pattern pattern = Pattern.compile("/DISPUTE (\\S+)\\s?(.*)?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);

            if (matcher.find()) {
                order = Long.valueOf(matcher.group(1));
                message = matcher.group(2);
            }
        }
    }

    private User getSender(Purchase purchase, Long currentUserId) {
        if(purchase.getSeller().getId().equals(currentUserId)) {
            return purchase.getSeller();
        } else {
            return purchase.getBuyer();
        }
    }

    private User getReceiver(Purchase purchase, Long currentUserId) {
        if(purchase.getSeller().getId().equals(currentUserId)) {
            return purchase.getBuyer();
        } else {
            return purchase.getSeller();
        }
    }

    private String getFormattedName(Purchase purchase) {
        return "%s %s".formatted(purchase.getId(), purchase.getName());
    }
    private String getFormattedDescription(Purchase purchase) {
        return "Buyer: %s | Seller: %s".formatted(purchase.getBuyer().getUsername(), purchase.getSeller().getUsername());
    }
}
