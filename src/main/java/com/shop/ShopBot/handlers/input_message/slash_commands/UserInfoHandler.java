package com.shop.ShopBot.handlers.input_message.slash_commands;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
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
        SendMessageHandler.CommandParts commandParts = new SendMessageHandler.CommandParts(update);
        if (!commandParts.getMessage().isEmpty() && !commandParts.getUsername().isEmpty()) {

            Optional<User> userOptional = userService.getUserByUsername(commandParts.getUsername());
            User sender = userService.getUser(update.getMessage().getFrom().getId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setParseMode(ParseMode.HTML);
                payload.setText(MessageText.USER_INFO.text().formatted(
                        user.getUsername(), user.equals(sender) ? "(you)" : "",
                        user.getUsername(),
                        user.getRating(),
                        0,
                        0,
                        0
                ));
                bot.process(payload);
            }

        }
    }

    @Data
    static class CommandParts {
        private String username;
        private String message;

        CommandParts(Update update) {
            String query = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
            Pattern pattern = Pattern.compile("/USER (\\S+)\\s?(.*)?", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(query);

            if (matcher.find()) {
                username = matcher.group(1);
                message = matcher.group(2);
            }
        }
    }
}
