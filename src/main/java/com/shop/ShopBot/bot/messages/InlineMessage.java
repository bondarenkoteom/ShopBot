package com.shop.ShopBot.bot.messages;

import com.shop.ShopBot.bot.keyboards.InlineKeyboard;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.database.service.ProductService;
import com.shop.ShopBot.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@Component
public class InlineMessage {

    @Autowired
    InlineKeyboard inlineKeyboard;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    private String chatId;

    private Integer messageId;

    public BotApiMethod<?> getErrorMessage(CallbackQuery buttonQuery) {
        return new SendMessage(buttonQuery.getMessage().getChatId().toString(), MessageText.UNKNOWN_MESSAGE.text());
    }

    public BotApiMethod<?> getWhatIsBTCMessage(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlineNextPageButton("HELP_SELL");
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();
        if (buttonQuery.getMessage().getText().equals(MessageText.HELP_MESSAGE.text())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageText.WHAT_IS_BTC.text());
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageText.WHAT_IS_BTC.text());
            editMessage.setReplyMarkup(keyboardMarkup);
            return editMessage;
        }
    }

    public BotApiMethod<?> getWhatCanISellMessage(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlineNextPageButton("HELP_BUYER");
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        if (buttonQuery.getMessage().getText().equals(MessageText.HELP_MESSAGE.text())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageText.WHAT_CAN_I_SELL.text());
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageText.WHAT_CAN_I_SELL.text());
            editMessage.setReplyMarkup(keyboardMarkup);
            return editMessage;
        }
    }

    public BotApiMethod<?> getBuyersFeaturesMessage(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlineNextPageButton("HELP_BTC");
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        if (buttonQuery.getMessage().getText().equals(MessageText.HELP_MESSAGE.text())) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(chatId);
            sendMessage.setText(MessageText.BUYERS_FEATURES.text());
            sendMessage.setReplyMarkup(keyboardMarkup);
            return sendMessage;
        } else {
            EditMessageText editMessage = new EditMessageText();
            editMessage.setMessageId(messageId);
            editMessage.setChatId(chatId);
            editMessage.setText(MessageText.BUYERS_FEATURES.text());
            editMessage.setReplyMarkup(keyboardMarkup);
            return editMessage;
        }
    }

    public BotApiMethod<?> getPurchasesMessage(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlinePurchasesButtons();
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Your last deals list");
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public BotApiMethod<?> getPurchaseInfoMessage(CallbackQuery buttonQuery) {
        InlineKeyboardMarkup keyboardMarkup = inlineKeyboard.getInlinePurchaseInfoButtons();
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Deal: 743fc92d\n" +
                "\n" +
                "Seller ID: /uCatsell\n" +
                "\n" +
                "Buyer ID: /ub71b71\n" +
                "\n" +
                "Deal started: 1 month ago\n" +
                "  \n" +
                "Deal status changed: 1 month ago\n" +
                "  \n" +
                "Deal current status: Completed");
        sendMessage.setReplyMarkup(keyboardMarkup);
        return sendMessage;
    }

    public BotApiMethod<?> getSetUsernameMessage(CallbackQuery buttonQuery) {
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        userService.setWaitFor(buttonQuery.getFrom().getId(), Trigger.USERNAME);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Enter username");

        return sendMessage;
    }

    public BotApiMethod<?> getAddGoodsImageMessage(CallbackQuery buttonQuery) {
        chatId = buttonQuery.getMessage().getChatId().toString();
        messageId = buttonQuery.getMessage().getMessageId();

        userService.setWaitFor(buttonQuery.getFrom().getId(), Trigger.NEW_PRODUCT_IMAGE);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(MessageText.CREATE_NEW_LOT.text());
        return sendMessage;
    }

    public BotApiMethod<?> getUserInfoMessage(CallbackQuery buttonQuery) {
        chatId = buttonQuery.getMessage().getChatId().toString();

        User user = userService.getUser(buttonQuery.getFrom().getId());
        String text;
        if (user == null) {
            text = "Fuck off, scum";
        } else {
            text = String.join("\n", user.getUsername(), user.getId().toString());
        }

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        return sendMessage;
    }


}
