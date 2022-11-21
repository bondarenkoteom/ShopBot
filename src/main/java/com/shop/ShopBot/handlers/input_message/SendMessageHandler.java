package com.shop.ShopBot.handlers.input_message;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@BotCommand(command = "/U.*", type = MessageType.INPUT_MESSAGE)
public class SendMessageHandler extends AbstractBaseHandler {


    @Override
    protected void handle(Update update) {
        CommandParts commandParts = new CommandParts(update);
        if (commandParts.getMessage().isEmpty()) {
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);
            payload.setText(MessageText.USER_INFO.text().formatted(
                    ""
            ));
            bot.process(payload);
        } else {
            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);
            payload.setText("🚀 The message was sent");
            bot.process(payload);
        }
    }

    @Data
    class CommandParts {
        private String username;
        private String message;

        CommandParts(Update update) {
            String query = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
            Pattern pattern = Pattern.compile("/[uU](\\S+)\\s?(.*)?");
            Matcher matcher = pattern.matcher(query);

            if (matcher.find()) {
                username = matcher.group(1);
                message = matcher.group(2);
            }
        }
    }
}
