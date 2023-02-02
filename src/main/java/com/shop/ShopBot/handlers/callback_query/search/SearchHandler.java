package com.shop.ShopBot.handlers.callback_query.search;

import com.shop.ShopBot.annotations.BotCommand;
import com.shop.ShopBot.constant.MessageType;
import com.shop.ShopBot.constant.SendMethod;
import com.shop.ShopBot.database.model.Product;
import com.shop.ShopBot.entity.Keys;
import com.shop.ShopBot.entity.Payload;
import com.shop.ShopBot.handlers.AbstractBaseHandler;
import com.shop.ShopBot.utils.Buttons;
import com.shop.ShopBot.utils.SimplePagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@BotCommand(command = "SEARCH .*", type = MessageType.CALLBACK_QUERY) //SEARCH -p -1 -m SEND_MESSAGE -
public class SearchHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        String searchQuery = keys.get("q");

        int pageNumber = Integer.parseInt(keys.get("p"));
        int elementsPerPage = 2;

        Page<Product> products;
        if (searchQuery.equals("all")) {
            Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
            products = productService.findAllProducts(pageable);
        } else {
            Sort sort = Sort.by(Sort.Direction.DESC, "rank");
            Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, sort);
            products = productService.fullTextSearch(searchQuery, pageable);
        }

        if (products.isEmpty()) return;

        Payload payload = new Payload(update);

        if (SendMethod.valueOf(keys.get("m")).equals(SendMethod.DELETE)) {
            payload.setSendMethod(SendMethod.DELETE);
            bot.process(payload);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);
        } else {
            payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
        }

        payload.setText("Below is the list of active lots. If you want to search some lots by the name or description just enter a string to search.");

        List<Product> originProducts = products.getContent();
        Map<String, String> buttons = IntStream.range(0, originProducts.size()).boxed().collect(Collectors.toMap(
                k -> "PRODUCT -p %s -i %s -m %s -q '%s'".formatted(pageNumber, k, SendMethod.DELETE, searchQuery),
                v -> "%s | %s".formatted(originProducts.get(v).getPrice(), originProducts.get(v).getProductName()), (a, b) -> a, LinkedHashMap::new
        ));

        Map<String, String> pagination = SimplePagination.twoButtonsSearchPagination(products, searchQuery, SendMethod.EDIT_TEXT, "SEARCH");

        payload.setKeyboardMarkup(Buttons.newBuilder()
                .setButtonsVertical(buttons)
                .setButtonsHorizontal(pagination)
                .build());

        bot.process(payload);


    }
}
