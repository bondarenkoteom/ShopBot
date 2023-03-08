package com.marketplace.utils;

import com.marketplace.constant.ButtonText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Buttons {

    private List<List<InlineKeyboardButton>> buttons;

    private List<InlineKeyboardButton> getSingleButtonRow(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }

    public static Builder newBuilder() {
        return new Buttons().new Builder();
    }

    public class Builder {

        public Builder setButtonsVertical(Map<String, String> buttons) {
            if (Buttons.this.buttons == null) {
                Buttons.this.buttons = buttons.entrySet().stream()
                        .map(b -> getSingleButtonRow(b.getValue(), b.getKey()))
                        .collect(Collectors.toCollection(ArrayList::new));
            } else {
                buttons.forEach((key, value) -> Buttons.this.buttons.add(getSingleButtonRow(value, key)));
            }
            return this;
        }

        public Builder setButtonsHorizontal(Map<String, String> buttons) {
            List<InlineKeyboardButton> keyboardButtonsRow = buttons.entrySet().stream()
                    .map(b -> {
                        InlineKeyboardButton button = new InlineKeyboardButton();
                        button.setText(b.getValue());
                        button.setCallbackData(b.getKey());
                        return button;
                    })
                    .collect(Collectors.toCollection(ArrayList::new));
            if (Buttons.this.buttons == null) {
                List<List<InlineKeyboardButton>> keyboardButtons = new ArrayList<>();
                keyboardButtons.add(keyboardButtonsRow);
                Buttons.this.buttons = keyboardButtons;
            } else {
                Buttons.this.buttons.add(keyboardButtonsRow);
            }
            return this;
        }

        public Builder setButton(String callBackQuery, String text) {
            if (Buttons.this.buttons == null) Buttons.this.buttons = new ArrayList<>();
            Buttons.this.buttons.add(getSingleButtonRow(text, callBackQuery));
            return this;
        }

        public Builder setGoBackButton(String callBackQuery) {
            setButton(callBackQuery, ButtonText.GO_BACK);
            return this;
        }

        public Builder setNextPageButton(String callBackQuery) {
            setButton(callBackQuery, ButtonText.NEXT_PAGE);
            return this;
        }

        public InlineKeyboardMarkup build() {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            inlineKeyboardMarkup.setKeyboard(Buttons.this.buttons);
            return inlineKeyboardMarkup;
        }
    }
}
