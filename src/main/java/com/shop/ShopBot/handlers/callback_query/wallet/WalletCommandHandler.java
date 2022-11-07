package com.shop.ShopBot.handlers.callback_query.wallet;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "WALLET#.+", type = MessageType.CALLBACK_QUERY)
public class WalletCommandHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String method = getMethod(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(method));
        payload.setText(MessageText.WALLET_DEFAULT_MESSAGE.text());

        Map<String, String> buttons = Map.of(
                "WALLET_ADD", ButtonText.WALLET_ADD.text(),
                "WALLET_HISTORY", ButtonText.WALLET_HISTORY.text(),
                "WALLET_WITHDRAW", ButtonText.WALLET_WITHDRAW.text()
        );
        payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
