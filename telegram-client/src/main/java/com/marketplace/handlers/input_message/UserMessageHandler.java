package com.marketplace.handlers.input_message;

import com.marketplace.constant.ProductStatus;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.Trigger;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Product;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.handlers.callback_query.search.SearchHandler;
import com.marketplace.handlers.callback_query.user_settings.UserSettingsCommandHandler;
import com.marketplace.handlers.callback_query.vendor_panel.VendorPanelCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
public class UserMessageHandler extends AbstractBaseHandler {

    @Autowired
    private ApplicationContext context;

    @Override
    public void handle(Update update) {

        Message message = update.getMessage();
        Trigger trigger = httpCoreInterface.triggerGet(message.getFrom().getId()).getTrigger();

        switch (trigger) {
            case USERNAME -> {
                Optional<User> optionalUser = httpCoreInterface.userGet(message.getFrom().getId(), null);

                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    user.setUsername(message.getText());
                    user.setWaitFor(Trigger.UNDEFINED);

                    httpCoreInterface.userUpdate(user);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully.""");
                    bot.process(payload);

                    UserSettingsCommandHandler userSettingsCommandHandler = context.getBean(UserSettingsCommandHandler.class);
                    update.getMessage().setText("USER_SETTINGS -m SEND_MESSAGE");
                    userSettingsCommandHandler.handle(update);
                }
            }
            case NEW_PRODUCT_IMAGE -> {
                httpCoreInterface.productEditingDelete(update.getCallbackQuery().getFrom().getId());

                String fileId = message.getDocument() == null ? message.getPhoto().get(1).getFileId() : message.getDocument().getFileId();

                Product product = new Product();
                product.setImageId(fileId);
                product.setOwnerId(message.getFrom().getId());
                product.setIsEditing(true);
                product.setStatus(ProductStatus.NOT_ACTIVE);
                httpCoreInterface.productUpdate(product);

                setTriggerValue(update, Trigger.NEW_PRODUCT_NAME);

                Payload payload = new Payload(update);
                payload.setSendMethod(SendMethod.SEND_MESSAGE);
                payload.setText("""
                        👍 The image was set successfully.

                        Please enter the short and clear name of your lot. Try not to use more than 100 symbols.""");
                bot.process(payload);
            }
            case EDIT_PRODUCT_IMAGE -> {
                String fileId = message.getDocument() == null ? message.getPhoto().get(0).getFileId() : message.getDocument().getFileId();

                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setImageId(fileId);
                    product.setIsEditing(false);

                    httpCoreInterface.productUpdate(product);

                    returnTriggerValue(update);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The image was set successfully.""");
                    bot.process(payload);
                }
            }
            case NEW_PRODUCT_NAME -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setProductName(message.getText());

                    httpCoreInterface.productUpdate(product);

                    setTriggerValue(update, Trigger.NEW_PRODUCT_DESCRIPTION);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully.

                        Please enter the description of your lot. Try not to use more than 2500 symbols.""");
                    bot.process(payload);
                }
            }
            case EDIT_PRODUCT_NAME -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setProductName(message.getText());
                    product.setIsEditing(false);

                    httpCoreInterface.productUpdate(product);

                    returnTriggerValue(update);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully.""");
                    bot.process(payload);
                }
            }
            case NEW_PRODUCT_DESCRIPTION -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setDescription(message.getText());

                    httpCoreInterface.productUpdate(product);

                    setTriggerValue(update, Trigger.NEW_PRODUCT_PRICE);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully.

                        Please enter the price of one key of your lot in BTC. For example  0.0001. Min. value is 0.00001""");
                    bot.process(payload);
                }
            }
            case EDIT_PRODUCT_DESCRIPTION -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setDescription(message.getText());
                    product.setIsEditing(false);

                    httpCoreInterface.productUpdate(product);

                    returnTriggerValue(update);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully.""");
                    bot.process(payload);
                }
            }
            case NEW_PRODUCT_PRICE -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setPrice(Double.parseDouble(message.getText()));

                    httpCoreInterface.productUpdate(product);

                    setTriggerValue(update, Trigger.NEW_PRODUCT_ITEMS);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully.

                        Send text file with items you want to sell""");
                    bot.process(payload);
                }
            }
            case EDIT_PRODUCT_PRICE -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setPrice(Double.parseDouble(message.getText()));
                    product.setIsEditing(false);

                    httpCoreInterface.productUpdate(product);

                    returnTriggerValue(update);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully.""");
                    bot.process(payload);
                }
            }
            case NEW_PRODUCT_ITEMS -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setIsEditing(true);

                    if (message.getDocument() != null) {
                        String filePath = telegramApiClient.getFilePath(message.getDocument().getFileId());
                        byte[] bytea = telegramApiClient.getDownloadFile(filePath);
                        product.setItems(new String(bytea).split("\n"));
                    } else {
                        product.setItems(message.getText().split("\n"));
                    }

                    httpCoreInterface.productUpdate(product);

                    setTriggerValue(update, Trigger.NEW_PRODUCT_INSTRUCTION);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully.
                                                
                        Send text of instructions how to use your product""");
                    bot.process(payload);
                }
            }
            case NEW_PRODUCT_INSTRUCTION -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setInstruction(message.getText());
                    product.setIsEditing(false);

                    httpCoreInterface.productUpdate(product);

                    returnTriggerValue(update);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully. (items)
                                                
                        Great! Your new lot has been created successfully! To Activate your lot please go to "Manage my lots" section and set appropriate lot status.""");
                    bot.process(payload);

                    VendorPanelCommandHandler vendorPanelCommandHandler = context.getBean(VendorPanelCommandHandler.class);
                    update.getMessage().setText("VENDOR_PANEL -m SEND_MESSAGE");
                    vendorPanelCommandHandler.handle(update);
                }

            }
            case EDIT_PRODUCT_ITEMS -> {
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(message.getFrom().getId());

                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setIsEditing(false);

                    if (message.getDocument() != null) {
                        String filePath = telegramApiClient.getFilePath(message.getDocument().getFileId());
                        byte[] bytea = telegramApiClient.getDownloadFile(filePath);
                        product.setItems(new String(bytea).split("\n"));
                    } else {
                        product.setItems(message.getText().split("\n"));
                    }

                    httpCoreInterface.productUpdate(product);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The value was set successfully. (items)""");
                    bot.process(payload);
                }
            }

            default -> {
                SearchHandler searchHandler = context.getBean(SearchHandler.class);
                String searchQuery = update.getMessage().getText();
                update.getMessage().setText("SEARCH -p 0 -m %s -q '%s'".formatted(SendMethod.SEND_MESSAGE, searchQuery));
                searchHandler.handle(update);
            }
        }
    }

}
