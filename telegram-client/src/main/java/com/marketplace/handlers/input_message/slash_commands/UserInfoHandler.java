package com.marketplace.handlers.input_message.slash_commands;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Payload;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@BotCommand(command = "/USER .*", type = MessageType.INPUT_MESSAGE)
public class UserInfoHandler extends AbstractBaseHandler {

    @Override
    protected void handle(Update update) {
        CommandParts commandParts = new CommandParts(update);
        if (!commandParts.getUsername().isEmpty()) {

            Optional<User> userOptional = httpCoreInterface.userGet(null, commandParts.getUsername());
            Optional<User> currentUsrRequestOptional = httpCoreInterface.userGet(update.getMessage().getFrom().getId(), null);

            if (userOptional.isPresent() && currentUsrRequestOptional.isPresent()) {
                User user = userOptional.get();
                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setParseMode(ParseMode.HTML);
                payload.setText(MessageText.USER_INFO.text().formatted(
                        user.getUsername(), user.equals(currentUsrRequestOptional.get()) ? "(you)" : "",
                        user.getUsername(),
                        user.getRating(),
                        user.getSells(),
                        0,
                        0
                ));
                bot.process(payload);
            }

        }
    }

    @Data
    private static class CommandParts {
        private String username;

        CommandParts(Update update) {
            String query = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
            Pattern pattern = Pattern.compile("/USER (\\S+)", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);

            if (matcher.find()) {
                username = matcher.group(1);
            }
        }
    }
}
