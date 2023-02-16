package com.shop.ShopBot.handlers.input_message.slash_commands;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Keyboard;
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
