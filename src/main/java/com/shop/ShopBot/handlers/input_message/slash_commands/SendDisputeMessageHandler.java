package com.shop.ShopBot.handlers.input_message.slash_commands;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Dispute;
import com.shop.ShopBot.database.model.Purchase;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@BotCommand(command = "/DISPUTE .*", type = MessageType.INPUT_MESSAGE)
public class SendDisputeMessageHandler extends AbstractBaseHandler {

    @Override
    protected void handle(Update update) {
        CommandParts commandParts = new CommandParts(update);
        if (!commandParts.getMessage().isEmpty() && commandParts.getOrder() != null) {

            Optional<Purchase> purchase = purchaseService.getById(commandParts.getOrder());
            User sender = userService.getUser(update.getMessage().getFrom().getId());
            if (purchase.isPresent()) {
                Dispute dispute = new Dispute();
                dispute.setText(commandParts.getMessage());
                dispute.setSender(sender);
                dispute.setPurchaseId(purchase.get().getId());
                dispute.setDate(new Date());
                disputeService.save(dispute);

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
