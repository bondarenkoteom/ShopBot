package com.shop.ShopBot.handlers.callback_query.buy_process;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@BotCommand(command = "CHAT .*", type = MessageType.CALLBACK_QUERY)
public class ChatHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        long sellerId = Long.parseLong(getKeys(update).get("i"));

        User user = userService.getUser(sellerId);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.SEND_MESSAGE);
        payload.setParseMode(ParseMode.HTML);
        payload.setText("""
                <b>Chat with seller</b>
                Send command to bot: <code>/message %s your message</code>
                For example: <code>/message %s I've got some problems with the activation code. It's expired.</code>
                You can see message chat in 'Messages' section.
                """.formatted(user.getUsername(), user.getUsername()));
        bot.process(payload);
    }
}
