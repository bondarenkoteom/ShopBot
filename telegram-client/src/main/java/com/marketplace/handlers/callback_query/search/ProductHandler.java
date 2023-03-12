package com.marketplace.handlers.callback_query.search;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.*;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Product;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.requests.SearchRequest;
import com.marketplace.requests.UserRequest;
import com.marketplace.utils.Buttons;
import com.marketplace.utils.SimplePagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@BotCommand(command = "PRODUCT .*", type = MessageType.CALLBACK_QUERY)
public class ProductHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        String searchQuery = keys.get("q");
        Category category = keys.get("c") != null ? Category.valueOf(keys.get("c")) : Category.ALL;

        int itemPosition = Integer.parseInt(keys.get("i"));

        int pageNumber = Integer.parseInt(keys.get("p"));
        int elementsPerPage = 5;

        Page<Product> products;
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setQuery(searchQuery);
        searchRequest.setCategory(category);

        if (searchQuery.isEmpty()) {
            products = httpCoreInterface.search(pageNumber, elementsPerPage, new String[]{}, searchRequest);
        } else {
            products = httpCoreInterface.search(pageNumber, elementsPerPage, new String[]{"rank", Sort.Direction.DESC.name()}, searchRequest);
        }

        if (products.isEmpty()) return;

        List<Product> originalProducts = products.getContent();

        if (originalProducts.get(itemPosition) != null) {
            Product currentProduct = originalProducts.get(itemPosition);

            Optional<User> userOptional = httpCoreInterface.userGet(currentProduct.getOwnerId(), null);

            if (userOptional.isEmpty()) return;


            Payload payload = new Payload(update);

            if (SendMethod.valueOf(keys.get("m")).equals(SendMethod.DELETE)) {
                payload.setSendMethod(SendMethod.DELETE);
                bot.process(payload);
                payload.setSendMethod(SendMethod.SEND_PHOTO);
            } else {
                payload.setSendMethod(SendMethod.valueOf(keys.get("m")));
            }

            payload.setText(getFormattedTextMessage(currentProduct, userOptional.get()));
            payload.setFileId(currentProduct.getImageId());

            Map<String, String> button = Map.of("BUY_CONFIRM -i %s -m %s".formatted(currentProduct.getId(), SendMethod.SEND_MESSAGE),
                    ButtonText.BUY.formatted(currentProduct.getPrice()));

            Map<String, String> pagination = SimplePagination.floatingItemPagination2(products, searchQuery, itemPosition, SendMethod.EDIT_MEDIA, "PRODUCT");

            Buttons.Builder builder = Buttons.newBuilder().setButtonsHorizontal(pagination);
            if (!Long.valueOf(payload.getChatId()).equals(currentProduct.getOwnerId())) {
                builder.setButtonsHorizontal(button);
            }
            builder.setGoBackButton("SEARCH -p %s -m %s -q '%s' -c %s".formatted(pageNumber, SendMethod.DELETE, searchQuery, category.name()));
            payload.setKeyboard(builder.build());
            bot.process(payload);
        }

    }

    private String getFormattedTextMessage(Product product, User user) {
        return MessageText.PRODUCT.text().formatted(
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                user.getUsername(),
                product.getRatingGood(), product.getRatingBad()
        );
    }
}
