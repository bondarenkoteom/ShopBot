package com.shop.ShopBot.handler;

import com.shop.ShopBot.bot.keyboards.InlineKeyboard;
import com.shop.ShopBot.constant.BotButtonNameEnum;
import com.shop.ShopBot.constant.BotMessageEnum;
import com.shop.ShopBot.bot.keyboards.ReplyKeyboard;
import com.shop.ShopBot.constant.CallbackDataPartsEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {

    ReplyKeyboard replyKeyboard;


    public BotApiMethod<?> answerMessage(Message message) {

        String chatId = message.getChatId().toString();

        String inputText = message.getText();

        if (inputText == null) {
            throw new IllegalArgumentException();
        } else if (inputText.equals("/start")) {
            return getStartMessage(chatId);
        } else if (inputText.equals(BotButtonNameEnum.HELP.getButtonName())) {
           SendMessage sendMessage = new  SendMessage(chatId, BotMessageEnum.HELP_MESSAGE.getMessage());
           sendMessage.enableMarkdown(true);
           return sendMessage;
        } else {
            return new SendMessage(chatId, BotMessageEnum.UNKNOWN_MESSAGE.getMessage());
        }

    }

    private SendMessage getStartMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, BotMessageEnum.HELP_MESSAGE1.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboard.getMainMenuKeyboard());
        return sendMessage;
    }

}
