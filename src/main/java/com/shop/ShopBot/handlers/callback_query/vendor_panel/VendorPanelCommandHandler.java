package com.shop.ShopBot.handlers.callback_query.vendor_panel;

import com.shop.ShopBot.constant.ButtonText;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.utils.Buttons;
import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

@Component
@BotCommand(command = "VENDOR_PANEL#.+", type = MessageType.CALLBACK_QUERY)
public class VendorPanelCommandHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        String method = getMethod(update);

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(method));
        payload.setText(MessageText.VENDOR_DEFAULT_MESSAGE.text());

        Map<String, String> firstRow = Map.of(
                "STATISTICS", "📈 Statistics"
        );

        Map<String, String> secondRow = Map.of(
                "MANAGE_LOTS", ButtonText.MANAGE_LOTS.text(),
                "CREATE_LOT", ButtonText.CREATE_LOT.text()
        );

        Map<String, String> thirdRow = Map.of(
                "DISPUTES", "⚠️ Disputes",
                "MESSAGES", "💬 Messages "
        );
        payload.setKeyboardMarkup(Buttons.newBuilder()
                .setButtonsHorizontal(firstRow)
                .setButtonsHorizontal(secondRow)
                .setButtonsHorizontal(thirdRow).build());

        bot.process(payload);
    }
}
