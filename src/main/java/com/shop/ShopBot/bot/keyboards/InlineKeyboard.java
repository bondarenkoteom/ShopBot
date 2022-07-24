package com.shop.ShopBot.bot.keyboards;

import com.shop.ShopBot.constant.BotMessageEnum;
import com.shop.ShopBot.constant.CallbackDataPartsEnum;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboard {

    public InlineKeyboardMarkup getInlineMessageButtonsWithHelp(String prefix) {
        InlineKeyboardMarkup inlineKeyboardMarkup = getInlineMessageButtons(prefix);
        inlineKeyboardMarkup.getKeyboard().add(getButton(
                "Помощь",
                prefix + CallbackDataPartsEnum.TASK_.name()
        ));
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineMessageButtons(String prefix) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        for (BotMessageEnum dictionary : BotMessageEnum.values()) {
            rowList.add(getButton(
                    dictionary.getMessage(),
                    prefix + dictionary.name()
            ));
        }

        if (!rowList.isEmpty()) {
            rowList.add(getButton(
                    "Все классы",
                    prefix + CallbackDataPartsEnum.USER_DICTIONARY.name()
            ));
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }
}
