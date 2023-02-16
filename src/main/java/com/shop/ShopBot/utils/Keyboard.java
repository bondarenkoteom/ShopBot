package com.shop.ShopBot.utils;

import com.shop.ShopBot.constant.ButtonText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.LinkedList;
import java.util.List;

public class Keyboard {

    private List<List<InlineKeyboardButton>> column;

    private List<InlineKeyboardButton> row;

    public static Keyboard newBuilder() {
        Keyboard keyboard = new Keyboard();
        keyboard.column = new LinkedList<>();
        return keyboard;
    }

    public Keyboard row() {
        addCurrentRowToColumn();
        row = new LinkedList<>();
        return this;
    }

    public Keyboard button(String command, String text) {
        if (row != null) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            button.setCallbackData(command);
            button.setText(text);
            row.add(button);
        }
        return this;
    }

    public Keyboard buttonBack(String command) {
        button(command, ButtonText.GO_BACK);
        return this;
    }

    public Keyboard buttonNext(String command) {
        button(command, ButtonText.NEXT_PAGE);
        return this;
    }

    public InlineKeyboardMarkup build() {
        addCurrentRowToColumn();
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(column);
        return inlineKeyboardMarkup;
    }

    private void addCurrentRowToColumn() {
        if(row != null && column != null) column.add(row);
    }
}
