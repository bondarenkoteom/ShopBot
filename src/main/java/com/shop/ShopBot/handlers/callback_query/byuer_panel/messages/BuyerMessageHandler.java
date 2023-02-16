package com.shop.ShopBot.handlers.callback_query.byuer_panel.messages;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Message;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import com.shop.ShopBot.utils.DateFormat;
import com.shop.ShopBot.utils.SimplePagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@Component
@BotCommand(command = "BUYER_MESSAGE .*", type = MessageType.CALLBACK_QUERY)
public class BuyerMessageHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Long userId = Long.parseLong(keys.get("i"));
        Long superUserId = update.getCallbackQuery().getFrom().getId();
        int pageNumber = Integer.parseInt(keys.get("p"));
        int elementsPerPage = 10;

        Payload payload = new Payload(update);
        payload.setSendMethod(SendMethod.valueOf(keys.get("m")));

        Page<Message> messages;
        if (pageNumber == -1) {
            messages = messageService.getChatMessages(superUserId, userId, PageRequest.of(0, elementsPerPage));
            if (messages.getTotalPages() > 1) {
                pageNumber = messages.getTotalPages() - 1;
                messages = messageService.getChatMessages(superUserId, userId, PageRequest.of(pageNumber, elementsPerPage));
            }
        } else {
            messages = messageService.getChatMessages(superUserId, userId, PageRequest.of(pageNumber, elementsPerPage));
        }

        String chatText = getFormattedChatText(messages, superUserId);
        if (chatText.isEmpty()) chatText = "No messages yet";
        payload.setText(chatText);
        payload.setParseMode(ParseMode.HTML);

        payload.setKeyboard(Buttons.newBuilder()
                .setButtonsHorizontal(SimplePagination.twoButtonsPagination(messages, "BUYER_MESSAGE", "-m EDIT_TEXT -i " + userId))
                .setGoBackButton("BUYER_MESSAGES -m %s".formatted(SendMethod.EDIT_TEXT)).build());
        bot.process(payload);
    }

    private String getFormattedChatText(Page<Message> messages, Long superUserId) {
        return messages.stream().map(message -> {
            if (message.getSender().getId().equals(superUserId)) {
                return String.format("<b># You</b> [%s]%n", DateFormat.format(message.getDate())) + message.getText();
            } else {
                return String.format("<b># %s</b> [%s]%n", message.getSender().getUsername(), DateFormat.format(message.getDate())) + message.getText();
            }
        }).collect(Collectors.joining("\n\n"));
    }
}
