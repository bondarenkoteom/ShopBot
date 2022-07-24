package com.shop.ShopBot.handler;

import com.shop.ShopBot.constant.BotMessageEnum;
import com.shop.ShopBot.constant.CallbackDataPartsEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CallbackQueryHandler {

    public BotApiMethod<?> processCallbackQuery(CallbackQuery buttonQuery) throws IOException {
        final String chatId = buttonQuery.getMessage().getChatId().toString();

        String data = buttonQuery.getData();

        if (data.equals(CallbackDataPartsEnum.TASK_.name() + CallbackDataPartsEnum.USER_DICTIONARY.name())) {
            return getDictionaryTasks(chatId);
        } else if (data.equals(CallbackDataPartsEnum.DICTIONARY_.name() + CallbackDataPartsEnum.USER_DICTIONARY.name())) {
            return null;
        } else {
            return new SendMessage();
        }

    }

//    private SendMessage handleDefaultDictionary(String chatId, String data) throws IOException {
//        if (data.startsWith(CallbackDataPartsEnum.TASK_.name())) {
//            DictionaryResourcePathEnum resourcePath = DictionaryResourcePathEnum.valueOf(
//                    data.substring(CallbackDataPartsEnum.TASK_.name().length())
//            );
//            return getDictionaryTasks(chatId, resourcePath.name(), resourcePath.getFileName());
//        } else if (data.startsWith(CallbackDataPartsEnum.DICTIONARY_.name())) {
//            return getDictionary(chatId, data.substring(CallbackDataPartsEnum.DICTIONARY_.name().length()));
//        } else {
//            return new SendMessage(chatId, BotMessageEnum.EXCEPTION_BAD_BUTTON_NAME_MESSAGE.getMessage());
//        }

    private SendMessage getDictionaryTasks(String chatId) throws IOException {
        return new SendMessage(chatId, BotMessageEnum.EXCEPTION_ILLEGAL_MESSAGE.getMessage());

    }
}
