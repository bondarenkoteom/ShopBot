package com.shop.ShopBot.handlers.callback_query.search;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "SEARCH_-?\\d+#.*", type = MessageType.CALLBACK_QUERY)
public class SearchHandler extends AbstractBaseHandler {
    @Override
    public void handle(Update update) {
        String method = getMethod(update);

        int pageNumber = Integer.parseInt(getAttribute("SEARCH_", update));

        if (pageNumber >= 0) {
            int countOfElements = 1;
            Pageable firstPageWithTwoElements = PageRequest.of(pageNumber, countOfElements);
            List<Product> products = productService.findAllProducts(firstPageWithTwoElements);

            Payload payload = new Payload(update);
            payload.setSendMethod(SendMethod.valueOf(method));
            payload.setText("Below is the list of active lots. If you want to search some lots by the name or description just enter a string to search.");

            Map<String, String> buttons = products.stream()
                    .collect(Collectors.toMap(k -> "PRODUCT_" + k.getId() + "#" + SendMethod.SEND_PHOTO, v -> "%s | %s".formatted(v.getPrice(), v.getProductName())));

            Map<String, String> pagination = new TreeMap<>(){{
                put("SEARCH_%d#%s".formatted(pageNumber - 1, SendMethod.EDIT_TEXT), "« Previous");
                put("SEARCH_%d#%s".formatted(pageNumber + 1, SendMethod.EDIT_TEXT), "Next »");
            }};
            payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).setButtonsHorizontal(pagination).build());

            bot.process(payload);
        }
    }
}
