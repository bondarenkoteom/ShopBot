package com.shop.ShopBot.bot.keyboards;

import com.shop.ShopBot.entity.AppKeyboardButton;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboard {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(new AppKeyboardButton("Test", "https://walletbot.me/main"));
//        firstRow.add(new KeyboardButton(ButtonNameEnum.BUYER_PANEL.getButtonName()));
//        firstRow.add(new KeyboardButton(ButtonNameEnum.VENDOR_PANEL.getButtonName()));

        KeyboardRow secondRow = new KeyboardRow();
//        secondRow.add(new KeyboardButton(ButtonNameEnum.USER_SETTINGS.getButtonName()));
//        secondRow.add(new KeyboardButton(ButtonNameEnum.WALLET.getButtonName()));
//        secondRow.add(new KeyboardButton(ButtonNameEnum.SUPPORT.getButtonName()));

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(firstRow);
        keyboardRows.add(secondRow);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }

}
