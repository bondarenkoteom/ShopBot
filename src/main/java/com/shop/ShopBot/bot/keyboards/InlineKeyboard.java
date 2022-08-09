package com.shop.ShopBot.bot.keyboards;

import com.shop.ShopBot.constant.MessageEnum;
import com.shop.ShopBot.constant.CallbackDataPartsEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboard {

    public InlineKeyboardMarkup getInlineHelpButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButton("What is BTC?", CallbackDataPartsEnum.HELP_.name() + "BTC"));
        rowList.add(getButton("What can I sell", CallbackDataPartsEnum.HELP_.name() + "SELL"));
        rowList.add(getButton("Support", CallbackDataPartsEnum.HELP_.name() + "SUPPORT"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineUserSettingsButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButton("Switch mode", CallbackDataPartsEnum.USER_SETTINGS_.name() + ""));
        rowList.add(getButton("Set name", CallbackDataPartsEnum.USER_SETTINGS_.name() + ""));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineWalletButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButton("Add", CallbackDataPartsEnum.WALLET_.name() + ""));
        rowList.add(getButton("History", CallbackDataPartsEnum.WALLET_.name() + ""));
        rowList.add(getButton("Withdraw", CallbackDataPartsEnum.WALLET_.name() + ""));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineBuyerPanelButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButton("", CallbackDataPartsEnum.BUYER_PANEL_.name() + ""));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineVendorPanelButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButton("", CallbackDataPartsEnum.VENDOR_PANEL_.name() + ""));
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
