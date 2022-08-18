package com.shop.ShopBot.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotConfig {

    @Value("${bot.path}")
    String botPath;

    @Value("${bot.username}")
    String botUsername;

    @Value("${bot.token}")
    String botToken;

    @Value("${bot.webhook.url}")
    String botWebhookUrl;

    @Value("${bot.internal.url}")
    String botInternalUrl;

}
