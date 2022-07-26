package com.shop.ShopBot.handlers;

import com.shop.ShopBot.Bot;
import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.bot.messages.InlineMessage;
import com.shop.ShopBot.bot.messages.ReplyMessage;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.service.UserService;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.input_message.UserMessageHandler;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.stream.Stream;

@Component
@Getter
@Setter
public class TelegramFacade {

    @Autowired
    InlineMessage inlineMessage;

    @Autowired
    ReplyMessage replyMessage;

    @Autowired
    UserService userService;

    @Autowired
    List<AbstractBaseHandler> handlers;

    @Autowired
    UserMessageHandler userMessageHandler;

    public void handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            processCallbackQuery(update);
        } else {
            processInputMessage(update);
        }
    }

    private AbstractBaseHandler getCallbackQueryHandler(String text) {
        return handlers.stream()
                .filter(h -> h.getClass().isAnnotationPresent(BotCommand.class))
                .filter(h -> h.getClass().getAnnotation(BotCommand.class).type().equals(MessageType.CALLBACK_QUERY))
                .filter(h ->
                        Stream.of(h.getClass().getAnnotation(BotCommand.class).command())
                                .anyMatch(c -> text.toUpperCase().matches(c))
                )
                .findAny().orElse(new AbstractBaseHandler() {
                    @Override
                    protected void handle(Update update) {
                        Payload payload = new Payload(update);
                        payload.setSendMethod(SendMethod.SEND_MESSAGE);
                        payload.setText("Unknown command: " + text);
                        userMessageHandler.bot.process(payload);
                    }
                });
    }

    private AbstractBaseHandler getInputMessageHandler(String text) {
        return handlers.stream()
                .filter(h -> h.getClass().isAnnotationPresent(BotCommand.class))
                .filter(h -> h.getClass().getAnnotation(BotCommand.class).type().equals(MessageType.INPUT_MESSAGE))
                .filter(h ->
                        Stream.of(h.getClass().getAnnotation(BotCommand.class).command())
                                .anyMatch(c -> text.toUpperCase().matches(c))
                )
                .findAny().orElse(userMessageHandler);
    }

    public void processCallbackQuery(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        userService.setWaitFor(callbackQuery.getFrom().getId(), Trigger.UNDEFINED);

        AbstractBaseHandler callbackQueryHandler = getCallbackQueryHandler(callbackQuery.getData());
        callbackQueryHandler.handle(update);
    }

    public void processInputMessage(Update update) {
        Message message = update.getMessage();
        String inputText;

        boolean isSimpleMessage = (message.getPhoto() == null || message.getDocument() == null) && message.getText() != null;

        if (!isSimpleMessage) {
            inputText = message.getDocument() == null ? message.getPhoto().get(0).getFileId() : message.getDocument().getFileId();
        } else {
            inputText = message.getText();
        }

        AbstractBaseHandler inputMessageHandler = getInputMessageHandler(inputText);
        inputMessageHandler.handle(update);
    }
}
