package com.shop.ShopBot;

import com.shop.ShopBot.handler.TelegramFacade;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.starter.SpringWebhookBot;

import java.io.File;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShopBot extends SpringWebhookBot {

    String botPath;

    String botUsername;

    String botToken;

    TelegramFacade telegramFacade;

    public ShopBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        super(setWebhook);
        this.telegramFacade = telegramFacade;
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramFacade.handleUpdate(update);
    }

    @SneakyThrows
    public void sendPhoto(String chatId, String imagePath) {
        File image = ResourceUtils.getFile("classpath:" + imagePath);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(image));
        sendPhoto.setChatId(chatId);
        execute(sendPhoto);
    }
}
