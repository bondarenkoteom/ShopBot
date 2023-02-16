package com.shop.ShopBot.handlers;

import com.shop.ShopBot.Bot;
import com.shop.ShopBot.api.TelegramApiClient;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.service.*;
import com.shop.ShopBot.entity.Keys;
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
    protected TelegramApiClient telegramApiClient;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected MessageService messageService;

    @Autowired
    protected DisputeService disputeService;

    @Autowired
    protected PurchaseService purchaseService;

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

        Trigger trigger = userService.getWaitFor(id);
        if (!trigger.equals(Trigger.UNDEFINED)) userService.setWaitFor(id, Trigger.UNDEFINED);
    }

    protected void setTriggerValue(Update update, Trigger newTriggerValue) {
        Long id = update.hasCallbackQuery() ? update.getCallbackQuery().getFrom().getId() : update.getMessage().getFrom().getId();

        Trigger trigger = userService.getWaitFor(id);
        if (!trigger.equals(newTriggerValue)) userService.setWaitFor(id, newTriggerValue);
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
