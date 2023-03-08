package com.marketplace.handlers.input_message.slash_commands;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageText;
import com.marketplace.constant.MessageType;
import com.marketplace.handlers.AbstractBaseHandler;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
@BotCommand(command = "/START", type = MessageType.INPUT_MESSAGE)
public class StartHandler extends AbstractBaseHandler {

    @Override
    @SneakyThrows
    public void handle(Update update) {
        Message message = update.getMessage();
        returnTriggerValue(update);

        userService.createIfAbsent(message.getFrom().getId(), message.getFrom().getUserName());
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), MessageText.START_MESSAGE.text());
        sendMessage.enableMarkdown(true);

        KeyboardRow firstRow = new KeyboardRow();
        firstRow.add(new KeyboardButton("Buyer panel"));
        firstRow.add(new KeyboardButton("Vendor panel"));

        KeyboardRow secondRow = new KeyboardRow();
        secondRow.add(new KeyboardButton("User settings"));
        secondRow.add(new KeyboardButton("Wallet"));
        secondRow.add(new KeyboardButton("Support"));

        List<KeyboardRow> keyboardRows = new ArrayList<>();
        keyboardRows.add(firstRow);
        keyboardRows.add(secondRow);

        final ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        bot.execute(sendMessage);
    }
}
