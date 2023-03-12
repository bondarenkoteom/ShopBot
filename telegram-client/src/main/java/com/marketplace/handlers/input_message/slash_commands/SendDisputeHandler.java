package com.marketplace.handlers.input_message.slash_commands;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Dispute;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Purchase;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
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
            Optional<User> senderOptional = httpCoreInterface.userGet(update.getMessage().getFrom().getId(), null);

            if (purchaseOptional.isPresent() && senderOptional.isPresent()) {
                Dispute dispute = new Dispute();
                dispute.setText(commandParts.getMessage());
                dispute.setSender(senderOptional.get());
                dispute.setPurchaseId(purchaseOptional.get().getId());
                dispute.setDate(new Date());

                httpCoreInterface.disputeCreate(dispute);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("🚀 The dispute message was sent");
                bot.process(payload);
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
}
