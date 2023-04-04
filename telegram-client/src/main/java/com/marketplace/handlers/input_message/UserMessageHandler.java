package com.marketplace.handlers.input_message;

import com.marketplace.constant.ProductStatus;
import com.marketplace.constant.SendMethod;
import com.marketplace.constant.Trigger;
import com.marketplace.entity.CustomMultipartFile;
import com.marketplace.entity.Payload;
import com.marketplace.entity.Product;
import com.marketplace.entity.User;
import com.marketplace.handlers.AbstractBaseHandler;
import com.marketplace.handlers.callback_query.search.SearchHandler;
import com.marketplace.handlers.callback_query.user_settings.UserSettingsCommandHandler;
import com.marketplace.handlers.callback_query.vendor_panel.VendorPanelCommandHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.net.URI;
import java.util.Optional;

@Component
public class UserMessageHandler extends AbstractBaseHandler {

    @Autowired
    private ApplicationContext context;

    @Override
    @SneakyThrows
    public void handle(Update update) {

        Long userId = getUserId(update);
        Message message = update.getMessage();
        Trigger trigger = httpCoreInterface.triggerGet(userId).getTrigger();


        switch (trigger) {
            case USERNAME -> {
                Optional<User> optionalUser = httpCoreInterface.userGet(userId, null);

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
                Optional<User> optionalUser = httpCoreInterface.userGet(userId, null);

                if (optionalUser.isPresent()) {
                    httpCoreInterface.productEditingDelete(userId);

                    String fileId = message.getDocument() == null ? message.getPhoto().get(1).getFileId() : message.getDocument().getFileId();

                    String filePath = telegramApiClient.getFilePath(fileId);
                    byte[] bytea = telegramApiClient.getDownloadFile(filePath);

                    WebClient webClient = WebClient.builder().build();
                    Long productImageId = webClient.post()
                            .uri("http://localhost:4230/api/v1/product/image")
                            .contentType(MediaType.MULTIPART_FORM_DATA)
                            .body(BodyInserters.fromMultipartData(fromByteArray(filePath, bytea)))
                            .retrieve()
                            .bodyToMono(Long.class)
                            .block();

                    Product product = new Product();
                    product.setImageId(fileId);
                    product.setRatingGood(0);
                    product.setRatingBad(0);
                    product.setOwner(optionalUser.get());
                    product.setIsEditing(true);
                    product.setStatus(ProductStatus.NOT_ACTIVE);
                    product.setProductImageId(productImageId);
                    httpCoreInterface.productUpdate(product);

                    setTriggerValue(update, Trigger.NEW_PRODUCT_NAME);

                    Payload payload = new Payload(update);
                    payload.setSendMethod(SendMethod.SEND_MESSAGE);
                    payload.setText("""
                        👍 The image was set successfully.

                        Please enter the short and clear name of your lot. Try not to use more than 100 symbols.""");
                    bot.process(payload);
                }
            }
            case EDIT_PRODUCT_IMAGE -> {
                String fileId = message.getDocument() == null ? message.getPhoto().get(0).getFileId() : message.getDocument().getFileId();

                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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
                Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(userId);

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

    public MultiValueMap<String, HttpEntity<?>> fromByteArray(String filename, byte[] file) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("fileParts", new ByteArrayResource(file)).filename(filename);
        return builder.build();
    }

}
