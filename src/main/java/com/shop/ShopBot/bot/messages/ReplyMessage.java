package com.shop.ShopBot.bot.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.ShopBot.api.TelegramApiClient;
import com.shop.ShopBot.bot.keyboards.InlineKeyboard;
import com.shop.ShopBot.bot.keyboards.ReplyKeyboard;
import com.shop.ShopBot.constant.MessageEnum;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.database.model.User;
import com.shop.ShopBot.database.service.ProductService;
import com.shop.ShopBot.database.service.UserService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReplyMessage {

    @Autowired
    ReplyKeyboard replyKeyboard;

    @Autowired
    InlineKeyboard inlineKeyboard;

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    TelegramApiClient telegramApiClient;

    public SendMessage getStartMessage(Message message) {
        userService.createIfAbsent(message.getFrom().getId(), message.getFrom().getUserName());
        SendMessage sendMessage = new SendMessage(message.getChatId().toString(), MessageEnum.START_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(replyKeyboard.getMainMenuKeyboard());
        return sendMessage;
    }

    public SendMessage getHelpMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.HELP_MESSAGE.getMessage());
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineHelpButtons());
        return sendMessage;
    }

    public SendMessage getUserSettingsMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.SETTINGS_DEFAULT_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineUserSettingsButtons());
        return sendMessage;
    }

    public SendMessage getWalletMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.WALLET_DEFAULT_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineWalletButtons());
        return sendMessage;
    }

    public SendMessage getBuyerPanelMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.BUYER_DEFAULT_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineBuyerPanelButtons());
        return sendMessage;
    }

    public SendMessage getVendorPanelMessage(String chatId) {
        SendMessage sendMessage = new SendMessage(chatId, MessageEnum.VENDOR_DEFAULT_MESSAGE.getMessage());
        sendMessage.setReplyMarkup(inlineKeyboard.getInlineVendorPanelButtons());
        return sendMessage;
    }

    public SendMessage getUserMessage(Trigger trigger, Message message) {
        switch (trigger) {
            case USERNAME -> {
                User user = userService.getUser(message.getFrom().getId());
                user.setUsername(message.getText());
                userService.save(user);
                return new SendMessage(message.getChatId().toString(), "Username successfully changed");
            }
            case ADD_PRODUCT_INFORMATION -> {

                String imageFilePath = telegramApiClient.getImageFilePath(message.getDocument() == null ? message.getPhoto().get(0).getFileId() : message.getDocument().getFileId());
                byte[] bytea = telegramApiClient.getDownloadImage(imageFilePath);

                String productName = getValueColumn(message.getCaption(), "name");
                String description = getValueColumn(message.getCaption(), "description");
                String price = getValueColumn(message.getCaption(), "price");

                Product product = new Product();
                product.setBytea(bytea);
                product.setOwnerId(message.getFrom().getId());
                product.setDescription(description);
                product.setPrice(price);
                product.setProductName(productName);
                productService.save(product);
                return new SendMessage(message.getChatId().toString(), "Information successfully add");
            }

            default -> {
                return new SendMessage(message.getChatId().toString(), "Unknown command");
            }
        }
    }

    private String getValueColumn(String message, String field) {
        Pattern pattern = Pattern.compile(field + ":(.*)");
        Matcher matcher = pattern.matcher(message);
        String result = "";
        if(matcher.find()) {
            result = matcher.group(1);
        }
        return result.trim();
    }
}
