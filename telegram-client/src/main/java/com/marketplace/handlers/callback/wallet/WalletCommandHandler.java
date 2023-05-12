package com.marketplace.handlers.callback.wallet;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.ButtonText;
import com.marketplace.constant.MessageType;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "WALLET .*", type = MessageType.CALLBACK_QUERY)
public class WalletCommandHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
        payload.setText(MessageText.WALLET_DEFAULT_MESSAGE.text());

        Map<String, String> buttons = Map.of(
                "WALLET_ADD", ButtonText.WALLET_ADD,
                "WALLET_HISTORY", ButtonText.WALLET_HISTORY,
                "WALLET_WITHDRAW", ButtonText.WALLET_WITHDRAW
        );
        payload.setKeyboard(Buttons.newBuilder().setButtonsVertical(buttons).build());
        bot.process(payload);
    }
}
