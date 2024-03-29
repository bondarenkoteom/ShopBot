package com.marketplace.handlers;

import com.marketplace.Bot;
import com.marketplace.client.HttpCoreInterface;
import com.marketplace.client.TelegramApiClient;
import com.marketplace.constant.Trigger;
import com.marketplace.entity.Keys;
import com.marketplace.requests.TriggerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractBaseHandler {

    @Autowired
    protected HttpCoreInterface httpCoreInterface;

    @Autowired
    protected TelegramApiClient telegramApiClient;

    @Autowired
    @Lazy
    protected Bot bot;

    protected abstract void handle(Update update);

    @Deprecated
    protected List<InlineKeyboardButton> getButtonList(String buttonName, String buttonCallBackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(buttonName);
        button.setCallbackData(buttonCallBackData);

        List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<>();
        keyboardButtonsRow.add(button);
        return keyboardButtonsRow;
    }

    protected void returnTriggerValue(Update update) {
        Long id = update.hasCallbackQuery() ? update.getCallbackQuery().getFrom().getId() : update.getMessage().getFrom().getId();

        TriggerRequest triggerRequest = new TriggerRequest();
        triggerRequest.setUserId(id);
        triggerRequest.setTrigger(Trigger.UNDEFINED);
        httpCoreInterface.triggerUpdate(triggerRequest);
    }

    protected void setTriggerValue(Update update, Trigger newTriggerValue) {
        Long id = update.hasCallbackQuery() ? update.getCallbackQuery().getFrom().getId() : update.getMessage().getFrom().getId();

        TriggerRequest triggerRequest = new TriggerRequest();
        triggerRequest.setUserId(id);
        triggerRequest.setTrigger(newTriggerValue);
        httpCoreInterface.triggerUpdate(triggerRequest);
    }

    protected Keys getKeys(Update update) {
        String query = update.hasCallbackQuery() ? update.getCallbackQuery().getData() : update.getMessage().getText();
        Keys keys = new Keys();
        Pattern pattern = Pattern.compile("(-[A-Za-z]{1} (?:[^'\\s]+|'.*?'))");
        Matcher matcher = pattern.matcher(query);

        while (matcher.find()) {
            String group = matcher.group();
            keys.put(group.substring(1, 2), group.substring(3).replaceAll("'", ""));
        }
        return keys;
    }

    protected Long getUserId(Update update) {
        return update.hasCallbackQuery() ? update.getCallbackQuery().getFrom().getId() : update.getMessage().getFrom().getId();
    }

}
