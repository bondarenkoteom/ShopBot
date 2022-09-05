package com.shop.ShopBot.config;

import com.shop.ShopBot.ShopBot;
import com.shop.ShopBot.handler.TelegramFacade;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultWebhook;

@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final BotConfig botConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getBotWebhookUrl()).build();
    }

    @Bean
    public ShopBot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
        ShopBot bot = new ShopBot(setWebhook, telegramFacade);

        bot.setBotPath(botConfig.getBotPath());
        bot.setBotUsername(botConfig.getBotUsername());
        bot.setBotToken(botConfig.getBotToken());

        return bot;
    }

    @Bean
    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        DefaultWebhook webhook = new DefaultWebhook();
        webhook.setInternalUrl(botConfig.getBotInternalUrl());
        return new TelegramBotsApi(DefaultBotSession.class, webhook);
    }
}