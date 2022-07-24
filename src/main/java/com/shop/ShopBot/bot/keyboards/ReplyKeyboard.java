package com.shop.ShopBot.bot.keyboards;

import com.shop.ShopBot.constant.BotButtonNameEnum;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReplyKeyboard {

    public ReplyKeyboardMarkup getMainMenuKeyboard() {
        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(new KeyboardButton(BotButtonNameEnum.CUSTOMER.getButtonName()));
        firstRow.add(new KeyboardButton(BotButtonNameEnum.SELLER.getButtonName()));

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(new KeyboardButton(BotButtonNameEnum.HELP.getButtonName()));

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
