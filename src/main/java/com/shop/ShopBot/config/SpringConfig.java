package com.shop.ShopBot.config;

import com.shop.ShopBot.handler.MessageHandler;
import com.shop.ShopBot.bot.ShopBot;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final BotConfig botConfig;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder().url(botConfig.getWebhookPath()).build();
    }

    @Bean
    public ShopBot springWebhookBot(SetWebhook setWebhook,
                                    MessageHandler messageHandler) {
        ShopBot bot = new ShopBot(setWebhook, messageHandler);

        bot.setBotPath(botConfig.getWebhookPath());
        bot.setBotUsername(botConfig.getBotName());
        bot.setBotToken(botConfig.getBotToken());

        return bot;
    }
}