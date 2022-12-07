package com.shop.ShopBot.api;

import com.shop.ShopBot.entity.FileResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.ApiResponse;
import org.telegram.telegrambots.meta.api.objects.File;

import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.Objects;

@Service
public class TelegramApiClient {
    private final String URL;
    private final String botToken;

    private final RestTemplate restTemplate;

    public TelegramApiClient(@Value("${api-url}") String URL,
                             @Value("${bot.token}") String botToken) {
        this.URL = URL;
        this.botToken = botToken;
        this.restTemplate = new RestTemplate();
    }

    public void webAppMessage(String chatId, Integer messageId) {
        try {
            restTemplate.execute(
                    MessageFormat.format("{0}bot{1}/deleteMessage?chat_id={2}&message_id={3}", URL, botToken, chatId, messageId),
                    HttpMethod.GET,
                    null,
                    null
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getFilePath(String fileId) {
        FileResponse fileResponse;
        try {
            fileResponse = restTemplate.getForObject(
                    MessageFormat.format("{0}bot{1}/getFile?file_id={2}", URL, botToken, fileId),
                    FileResponse.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assert fileResponse != null;
        if (fileResponse.isOk()) return fileResponse.getResult().getFilePath();
        else throw new IllegalStateException();
    }

    public byte[] getDownloadFile(String filePath) {
        byte[] bytea;
        try {
            bytea = restTemplate.getForObject(MessageFormat.format("{0}/file/bot{1}/{2}", URL, botToken, filePath), byte[].class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bytea;
    }

    public java.io.File getDocumentFile(String fileId) {
        try {
            return restTemplate.execute(
                    Objects.requireNonNull(getDocumentTelegramFileUrl(fileId)),
                    HttpMethod.GET,
                    null,
                    clientHttpResponse -> {
                        java.io.File ret = java.io.File.createTempFile("download", "tmp");
                        StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
                        return ret;
                    });
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private String getDocumentTelegramFileUrl(String fileId) {
        try {
            ResponseEntity<ApiResponse<org.telegram.telegrambots.meta.api.objects.File>> response = restTemplate.exchange(
                    MessageFormat.format("{0}bot{1}/getFile?file_id={2}", URL, botToken, fileId),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<ApiResponse<org.telegram.telegrambots.meta.api.objects.File>>() {
                    }
            );
            return Objects.requireNonNull(response.getBody()).getResult().getFileUrl(this.botToken);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}