package com.shop.ShopBot.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shop.ShopBot.ShopBot;
import com.shop.ShopBot.bot.messages.ReplyMessage;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class MessageHandler {
    @Autowired
    ReplyMessage replyMessage;
    @Autowired
    UserService userService;
    @Autowired
    ShopBot shopBot;

    public BotApiMethod<?> answerMessage(Message message) throws JsonProcessingException {

        Trigger trigger = userService.getWaitFor(message.getFrom().getId());

        String chatId = message.getChatId().toString();

        String inputText;

        boolean isSimpleMessage = (message.getPhoto() == null || message.getDocument() == null) && message.getText() != null;

        if (!isSimpleMessage) {
            inputText = message.getDocument() == null ? message.getPhoto().get(0).getFileId() : message.getDocument().getFileId();
        } else {
            inputText = message.getText();
        }


        switch (inputText) {
            case "/start" -> {
                returnTriggerValue(trigger, message);
                return replyMessage.getStartMessage(message);
            }
            case "User settings" -> {
                returnTriggerValue(trigger, message);
                return replyMessage.getUserSettingsMessage(chatId);
            }
            case "Wallet" -> {
                returnTriggerValue(trigger, message);
                return replyMessage.getWalletMessage(chatId);
            }
            case "Buyer panel" -> {
                returnTriggerValue(trigger, message);
                return replyMessage.getBuyerPanelMessage(chatId);
            }
            case "Vendor panel" -> {
                returnTriggerValue(trigger, message);
                return replyMessage.getVendorPanelMessage(chatId);
            }
            case "Support" -> {
                returnTriggerValue(trigger, message);
                return replyMessage.getHelpMessage(chatId);
            }
//            case null -> throw new IllegalArgumentException();
            default -> {
                return replyMessage.getUserMessage(trigger, message);
            }
        }
    }

    public SendMessage handleInputMessage(Message message) {
        String inputMsg = message.getText();
        String chatId = message.getChatId().toString();
        SendMessage replyMessage = null;

        switch (inputMsg) {
            case "/Get information about product":
                shopBot.sendPhoto(chatId, "http://localhost:5555/get-image-with-media-type");
                break;
        }
        return replyMessage;
    }


    private void returnTriggerValue(Trigger trigger, Message message) {
        if (!trigger.equals(Trigger.UNDEFINED)) userService.setWaitFor(message.getFrom().getId(), Trigger.UNDEFINED);
    }


}
