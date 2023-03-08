package com.marketplace.handlers.input_message.slash_commands;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.database.model.Message;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.database.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@BotCommand(command = "/MESSAGE .*", type = MessageType.INPUT_MESSAGE)
public class SendMessageHandler extends AbstractBaseHandler {

    @Override
    protected void handle(Update update) {
        CommandParts commandParts = new CommandParts(update);
        if (!commandParts.getMessage().isEmpty() && !commandParts.getUsername().isEmpty()) {

            Optional<User> receiver = userService.getUserByUsername(commandParts.getUsername());
            User sender = userService.getUser(update.getMessage().getFrom().getId());
            if (receiver.isPresent() && !receiver.get().equals(sender)) {
                Message message = new Message();
                message.setText(commandParts.getMessage());
                message.setSender(sender);
                message.setReceiver(receiver.get());
                message.setDate(new Date());
                messageService.save(message);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("🚀 The message was sent");
                bot.process(payload);
            }
            
        }
    }

    @Data
    private static class CommandParts {
        private String username;
        private String message;

        CommandParts(Update update) {
            String query = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
            Pattern pattern = Pattern.compile("/MESSAGE (\\S+)\\s?(.*)?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);

            if (matcher.find()) {
                username = matcher.group(1);
                message = matcher.group(2);
            }
        }
    }
}
