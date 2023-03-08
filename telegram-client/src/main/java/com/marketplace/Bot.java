package com.marketplace;

import com.marketplace.handlers.TelegramFacade;
import com.marketplace.entity.Payload;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageMedia;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bot extends TelegramLongPollingBot {

    String botPath;

    String botUsername;

    String botToken;

    TelegramFacade telegramFacade;

    public Bot(TelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public void onUpdateReceived(Update update) {
        telegramFacade.handleUpdate(update);
    }

    public void process(Payload payload) {
        switch (payload.getSendMethod()) {
            case SEND_MESSAGE -> sendMessage(payload);
            case EDIT_TEXT -> executeEditText(payload);
            case SEND_PHOTO -> sendPhoto(payload);
            case EDIT_CAPTION -> executeEditCaption(payload);
            case EDIT_MEDIA -> executeEditMedia(payload);
            case DELETE -> executeDelete(payload);
            case SEND_DOCUMENT -> sendDocument(payload);
        }
    }

    @SneakyThrows
    private void sendMessage(Payload payload) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(payload.getChatId());
        sendMessage.setText(payload.getText());
        sendMessage.setReplyMarkup(payload.getKeyboard());
        sendMessage.setParseMode(payload.getParseMode());
        execute(sendMessage);
    }

    @SneakyThrows
    private void executeEditText(Payload payload) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(payload.getChatId());
        editMessageText.setText(payload.getText());
        editMessageText.setMessageId(payload.getMessageId());
        editMessageText.setReplyMarkup(payload.getKeyboard());
        editMessageText.setParseMode(payload.getParseMode());
        execute(editMessageText);
    }

    @SneakyThrows
    private void sendPhoto(Payload payload) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(payload.getFileId()));
        sendPhoto.setChatId(payload.getChatId());
        sendPhoto.setCaption(payload.getText());
        sendPhoto.setReplyMarkup(payload.getKeyboard());
        sendPhoto.setParseMode(ParseMode.HTML);
        execute(sendPhoto);
    }

    @SneakyThrows
    private void executeEditCaption(Payload payload) {
        EditMessageCaption editMessageCaption = new EditMessageCaption();
        editMessageCaption.setChatId(payload.getChatId());
        editMessageCaption.setCaption(payload.getText());
        editMessageCaption.setMessageId(payload.getMessageId());
        editMessageCaption.setReplyMarkup(payload.getKeyboard());
        editMessageCaption.setParseMode(ParseMode.HTML);
        execute(editMessageCaption);
    }

    @SneakyThrows
    private void executeEditMedia(Payload payload) {
        EditMessageMedia editMessageMedia = new EditMessageMedia();
        InputMediaPhoto inputMediaPhoto = new InputMediaPhoto(payload.getFileId());
        inputMediaPhoto.setCaption(payload.getText());
        inputMediaPhoto.setParseMode(ParseMode.HTML);
        editMessageMedia.setMedia(inputMediaPhoto);
        editMessageMedia.setChatId(payload.getChatId());
        editMessageMedia.setMessageId(payload.getMessageId());
        editMessageMedia.setReplyMarkup(payload.getKeyboard());
        execute(editMessageMedia);
    }

    @SneakyThrows
    private void executeDelete(Payload payload) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(payload.getChatId());
        deleteMessage.setMessageId(payload.getMessageId());
        execute(deleteMessage);
    }

    @SneakyThrows
    private void sendDocument(Payload payload) {
        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(payload.getDocument());
        sendDocument.setChatId(payload.getChatId());
        execute(sendDocument);
    }
}
