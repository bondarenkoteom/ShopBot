package com.marketplace.handlers.callback.vendor_panel.messages;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Message;
import com.marketplace.entity.Payload;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import com.marketplace.utils.DateFormat;
import com.marketplace.utils.SimplePagination;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "SELLER_MESSAGE .*", type = MessageType.CALLBACK_QUERY)
public class SellerMessageHandler extends AbstractBaseHandler {

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
            messages = httpCoreInterface.messagesGet(0, elementsPerPage, new String[]{}, superUserId, userId);
            if (messages.getTotalPages() > 1) {
                pageNumber = messages.getTotalPages() - 1;
                messages = httpCoreInterface.messagesGet(pageNumber, elementsPerPage, new String[]{}, superUserId, userId);
            }
        } else {
            messages = httpCoreInterface.messagesGet(pageNumber, elementsPerPage, new String[]{}, superUserId, userId);
        }

        Optional<User> optionalUser = httpCoreInterface.userGet(userId, null);
        if (optionalUser.isPresent()) {
            String chatText = getFormattedChatText(messages, optionalUser.get(), superUserId);
            if (chatText.isEmpty()) chatText = "No messages yet";
            payload.setText(chatText);
            payload.setParseMode(ParseMode.HTML);

            payload.setKeyboard(Buttons.newBuilder()
                    .setButtonsHorizontal(SimplePagination.twoButtonsPagination(messages, "SELLER_MESSAGE", "-m EDIT_TEXT -i " + userId))
                    .setGoBackButton("SELLER_MESSAGES -m %s".formatted(SendMethod.EDIT_TEXT)).build());
            bot.process(payload);
        }
    }

    private String getFormattedChatText(Page<Message> messages, User user, Long superUserId) {
        return messages.stream().map(message -> {
            if (user.getId().equals(superUserId)) {
                return String.format("<b># You</b> [%s]%n", DateFormat.format(message.getDate())) + message.getText();
            } else {
                return String.format("<b># %s</b> [%s]%n", user.getUsername(), DateFormat.format(message.getDate())) + message.getText();
            }
        }).collect(Collectors.joining("\n\n"));
    }
}
