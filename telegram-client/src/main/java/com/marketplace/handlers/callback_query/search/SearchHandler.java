package com.marketplace.handlers.callback_query.search;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.Category;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.database.model.Product;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.utils.Buttons;
import com.marketplace.utils.SimplePagination;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
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
        Payload payload = new Payload(update);
        Keys keys = getKeys(update);

        String searchQuery = keys.get("q");
        Category category = keys.get("c") != null ? Category.valueOf(keys.get("c")) : Category.ALL;

        int pageNumber = Integer.parseInt(keys.get("p"));
        int elementsPerPage = 5;

        Page<Product> products;
        if (searchQuery.isEmpty()) {
            Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
            if (category == Category.ALL) {
                products = productService.findAllProducts(pageable);
            } else {
                products = productService.findAllProducts(category, pageable);
            }
        } else {
            Sort sort = Sort.by(Sort.Direction.DESC, "rank");
            Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, sort);
            if (category == Category.ALL) {
                products = productService.fullTextSearch(searchQuery, pageable);
            } else {
                products = productService.fullTextSearch(searchQuery, category, pageable);
            }
        }

        if (products.isEmpty()) {
            payload.setText("Nothing found, but here is all items that we got");
            Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
            products = productService.findAllProducts(pageable);
        } else {
            payload.setText("Below is the list of active lots. If you want to search some lots by the name or description just enter a string to search.");
        }

        if (SendMethod.valueOf(keys.get("m")).equals(SendMethod.DELETE)) {
            payload.setSendMethod(SendMethod.DELETE);
            bot.process(payload);
            payload.setSendMethod(SendMethod.SEND_MESSAGE);
        } else {
            payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
        }

        List<Product> originProducts = products.getContent();
        Map<String, String> buttons = IntStream.range(0, originProducts.size()).boxed().collect(Collectors.toMap(
                k -> "PRODUCT -p %s -i %s -m %s -q '%s' -c %s".formatted(pageNumber, k, SendMethod.DELETE, searchQuery, category.name()),
                v -> "%s | %s".formatted(originProducts.get(v).getPrice(), originProducts.get(v).getProductName()), (a, b) -> a, LinkedHashMap::new
        ));

        Map<String, String> pagination = SimplePagination.twoButtonsPagination(products, "SEARCH", "-m EDIT_TEXT -q '" + searchQuery + "' -c " + category.name());

        payload.setKeyboard(Buttons.newBuilder()
                .setButtonsVertical(buttons)
                .setButtonsHorizontal(pagination)
                .build());

        bot.process(payload);
    }
}
