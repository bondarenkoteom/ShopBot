package com.marketplace.config;

import com.marketplace.Bot;
import com.marketplace.handlers.TelegramFacade;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class SpringConfig {
    private final BotConfig botConfig;

    @Bean
    public Bot springLongPollingBot(TelegramFacade telegramFacade) {
        Bot bot = new Bot(telegramFacade);

        bot.setBotPath(botConfig.getBotPath());
        bot.setBotUsername(botConfig.getBotUsername());
        bot.setBotToken(botConfig.getBotToken());

        return bot;
    }
//    @Bean
//    public SetWebhook setWebhookInstance() {
//        return SetWebhook.builder().url(botConfig.getBotWebhookUrl()).build();
//    }
//
//    @Bean
//    public Bot springWebhookBot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
//        Bot bot = new Bot(setWebhook, telegramFacade);
//
//        bot.setBotPath(botConfig.getBotPath());
//        bot.setBotUsername(botConfig.getBotUsername());
//        bot.setBotToken(botConfig.getBotToken());
//
//        return bot;
//    }
//
//    @Bean
//    public TelegramBotsApi telegramBotsApi() throws TelegramApiException {
//        DefaultWebhook webhook = new DefaultWebhook();
//        webhook.setInternalUrl(botConfig.getBotInternalUrl());
//        return new TelegramBotsApi(DefaultBotSession.class, webhook);
//    }

//    public Bot(SetWebhook setWebhook, TelegramFacade telegramFacade) {
//        super(setWebhook);
//        this.telegramFacade = telegramFacade;
//    }
//
//    @Override
//    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
//        return telegramFacade.handleUpdate(update);
//    }
}