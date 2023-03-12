package com.marketplace.handlers.callback_query.vendor_panel.manage_my_lots;

import com.marketplace.annotations.BotCommand;
import com.marketplace.constant.MessageType;
import com.marketplace.constant.SendMethod;
import com.marketplace.entity.Keys;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Product;
import com.marketplace.handlers.AbstractBaseHandler;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Optional;

@Component
@BotCommand(command = "GET_LOT_ITEMS .*", type = MessageType.CALLBACK_QUERY)
public class GetLotItemsHandler extends AbstractBaseHandler {

    @Override
    public void handle(Update update) {
        Keys keys = getKeys(update);

        Optional<Product> productOptional = httpCoreInterface.productGet(Long.valueOf(keys.get("i")));

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            if (product.getItems() != null && product.getItems().length > 0) {
                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_DOCUMENT);
                String itemsString = String.join("\n", product.getItems());
                InputStream inputStream = IOUtils.toInputStream(itemsString, Charset.defaultCharset());
                payload.setDocument(new InputFile(inputStream, "items"));
                bot.process(payload);
            }

        }
    }

}
