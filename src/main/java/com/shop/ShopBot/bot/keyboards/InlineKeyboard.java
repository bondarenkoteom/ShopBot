package com.shop.ShopBot.bot.keyboards;

import com.shop.ShopBot.constant.CallbackDataPartsEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
public class InlineKeyboard {

    public InlineKeyboardMarkup getInlineHelpButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("What is BTC?", CallbackDataPartsEnum.HELP_.name() + "BTC"));
        rowList.add(getButtonList("What can I sell", CallbackDataPartsEnum.HELP_.name() + "SELL"));
        rowList.add(getButtonList("Buyer's features", CallbackDataPartsEnum.HELP_.name() + "BUYER"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineNextPageButton(String buttonQuery) {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("Next page", buttonQuery));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineUserSettingsButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("Switch mode", CallbackDataPartsEnum.USER_SETTINGS_.name() + ""));
        rowList.add(getButtonList("Set name", CallbackDataPartsEnum.USER_SETTINGS_.name() + ""));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineWalletButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("Add", CallbackDataPartsEnum.WALLET_.name() + ""));
        rowList.add(getButtonList("History", CallbackDataPartsEnum.WALLET_.name() + ""));
        rowList.add(getButtonList("Withdraw", CallbackDataPartsEnum.WALLET_.name() + ""));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineBuyerPanelButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("Purchases", CallbackDataPartsEnum.BUYER_PANEL_.name() + "PURCHASES"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlinePurchasesButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("✅ 7878dsv (1 day ago)", "PURCHASE_1"));
        rowList.add(getButtonList("✅ lk78dsv (1 day ago)", "PURCHASE_2"));
        rowList.add(getButtonList("❌ 68c8dsv (4 day ago)", "PURCHASE_3"));
        rowList.add(getButtonList("❌ o878dsv (3 day ago)", "PURCHASE_4"));
        rowList.add(getButtonList("✅ d87fdsv (2 day ago)", "PURCHASE_5"));
        rowList.add(getButtonList("✅ hy78dsv (1 day ago)", "PURCHASE_6"));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlinePurchaseInfoButtons() {
        List<InlineKeyboardButton> columnList = new ArrayList<>();
        columnList.add(getButton("✅ Close order", "CLOSE_ORDER"));
        columnList.add(getButton("⁉️Open dispute", "DISPUTE"));
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(columnList);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    public InlineKeyboardMarkup getInlineVendorPanelButtons() {
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        rowList.add(getButtonList("", CallbackDataPartsEnum.VENDOR_PANEL_.name() + ""));
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }

    private List<InlineKeyboardButton> getButtonList(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }

    private InlineKeyboardButton getButton(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);
        return button;
    }
}
