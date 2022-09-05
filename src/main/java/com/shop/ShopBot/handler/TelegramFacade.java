package com.shop.ShopBot.handler;

import com.shop.ShopBot.ShopBot;
import com.shop.ShopBot.bot.messages.InlineMessage;
import com.shop.ShopBot.bot.messages.ReplyMessage;
import com.shop.ShopBot.constant.Trigger;
import com.shop.ShopBot.database.service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;

@Component
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TelegramFacade {

    ShopBot shopBot;

    InlineMessage inlineMessage;

    ReplyMessage replyMessage;

    UserService userService;

    public TelegramFacade(InlineMessage inlineMessage, ReplyMessage replyMessage, UserService userService,
                          @Lazy ShopBot shopBot) {

        this.inlineMessage = inlineMessage;
        this.replyMessage = replyMessage;
        this.userService = userService;
        this.shopBot = shopBot;
    }

    public BotApiMethod<?> handleUpdate(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            return processCallbackQuery(callbackQuery);
        } else {
            Message message = update.getMessage();
            if (message != null) {
                return handleInputMessage(update.getMessage());
            }
        }
        return null;
    }

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) {

        userService.setWaitFor(buttonQuery.getFrom().getId(), Trigger.UNDEFINED);

        return switch (buttonQuery.getData()) {
            case "HELP_BTC" -> inlineMessage.getWhatIsBTCMessage(buttonQuery);
            case "HELP_SELL" -> inlineMessage.getWhatCanISellMessage(buttonQuery);
            case "HELP_BUYER" -> inlineMessage.getBuyersFeaturesMessage(buttonQuery);

            case "BUYER_PANEL_PURCHASES" -> inlineMessage.getPurchasesMessage(buttonQuery);
            case String s && s.startsWith("PURCHASE_") -> inlineMessage.getPurchaseInfoMessage(buttonQuery);

            case "SET_USERNAME" -> inlineMessage.getSetUsernameMessage(buttonQuery);
            case "USER_INFO" -> inlineMessage.getUserInfoMessage(buttonQuery);

            case "VENDOR_PANEL_PRODUCT" -> inlineMessage.getAddGoodsImageMessage(buttonQuery);
            case "VENDOR_PANEL_INFORMATION_ABOUT_PRODUCT" -> {
                shopBot.sendPhoto(buttonQuery.getMessage().getChatId().toString(), "static/images/wizard_logo.jpg");
                yield inlineMessage.getAddGoodsImageMessage(buttonQuery);
            }
            case null -> throw new IllegalArgumentException();
            default -> inlineMessage.getErrorMessage(buttonQuery);
        };
    }

    public BotApiMethod<?> handleInputMessage(Message message) {

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
            default -> {
                return replyMessage.getUserMessage(trigger, message);
            }
        }
    }

    private void returnTriggerValue(Trigger trigger, Message message) {
        if (!trigger.equals(Trigger.UNDEFINED)) userService.setWaitFor(message.getFrom().getId(), Trigger.UNDEFINED);
    }
}
