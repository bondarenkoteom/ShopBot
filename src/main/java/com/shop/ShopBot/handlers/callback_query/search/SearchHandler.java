package com.shop.ShopBot.handlers.callback_query.search;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageText;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.util.*;
import java.util.stream.Collectors;

@Component
@BotCommand(command = "SEARCH .*", type = MessageType.CALLBACK_QUERY) //SEARCH -p -1 -m SEND_MESSAGE -
public class SearchHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        String searchQuery = keys.get("q");

        Sort sort = Sort.by(Sort.Direction.DESC, "rank");

        int pageNumber = Integer.parseInt(keys.get("p"));
        int previousPage = pageNumber;
        int nextPage = pageNumber;

        if (pageNumber >= 0) {
            int countOfElements = 2;
            Pageable firstPageWithTwoElements = PageRequest.of(pageNumber, countOfElements, sort);
            LinkedList<Product> products = productService.fullTextSearch(firstPageWithTwoElements, searchQuery);
            LinkedList<Product> previousList = null;
            LinkedList<Product> nextList = null;

            if (products.isEmpty()) return;

            if (--previousPage >= 0) {
                Pageable previousPageWithTwoElements = PageRequest.of(previousPage, countOfElements, sort);
                previousList = productService.fullTextSearch(previousPageWithTwoElements, searchQuery);
            }

            if (++nextPage >= 0) {
                Pageable previousPageWithTwoElements = PageRequest.of(nextPage, countOfElements, sort);
                nextList = productService.fullTextSearch(previousPageWithTwoElements, searchQuery);
            }

            Payload payload = new Payload(update);

            if (SendMethod.valueOf(keys.get("m")).equals(SendMethod.DELETE)) {
                payload.setSendMethod(SendMethod.DELETE);
                bot.process(payload);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
            } else {
                payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
            }

            payload.setText("Below is the list of active lots. If you want to search some lots by the name or description just enter a string to search.");

            Map<String, String> buttons = products.stream().collect(Collectors.toMap(
                    k -> "PRODUCT -p %s -i %s -m %s -q '%s'".formatted(pageNumber, k.getId(), SendMethod.DELETE, searchQuery),
                    v -> "%s | %s".formatted(v.getPrice(), v.getProductName()), (a, b) -> a, LinkedHashMap::new
            ));

            Map<String, String> pagination = new LinkedHashMap<>();
            if (previousList != null && !previousList.isEmpty()) {
                pagination.put("SEARCH -p %s -m %s -q '%s'".formatted(pageNumber - 1, SendMethod.EDIT_TEXT, searchQuery), "« Previous");
            }
            if (nextList != null && !nextList.isEmpty()) {
                pagination.put("SEARCH -p %s -m %s -q '%s'".formatted(pageNumber + 1, SendMethod.EDIT_TEXT, searchQuery), "Next »");
            }
            payload.setKeyboardMarkup(Buttons.newBuilder().setButtonsVertical(buttons).setButtonsHorizontal(pagination).build());

            bot.process(payload);
        }

    }
}
