package com.marketplace.config;

import com.marketplace.Bot;
import com.marketplace.client.HttpCoreClient;
import com.marketplace.client.HttpCoreInterface;
import com.marketplace.handlers.TelegramFacade;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
@AllArgsConstructor
public class SpringConfig {

    private final BotConfig botConfig;

    @Bean
    public HttpCoreInterface httpCoreInterface(HttpCoreClient httpCoreClient) {
        return httpCoreClient.getHttpInterface();
    }

    @Bean
    TelegramBotsApi telegramBotsApi() throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class);
    }

    @Bean
    public Bot springLongPollingBot(TelegramFacade telegramFacade, TelegramBotsApi telegramBotsApi) throws TelegramApiException {
        Bot bot = new Bot(telegramFacade);
        bot.setBotPath(botConfig.getBotPath());
        bot.setBotUsername(botConfig.getBotUsername());
        bot.setBotToken(botConfig.getBotToken());
        telegramBotsApi.registerBot(bot);
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