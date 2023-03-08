package com.marketplace.websocket;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy;
//import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

//@Configuration
//@EnableWebFlux
class ReactiveWebSocketConfiguration {

//    @Bean
//    Executor executor() {
//        return Executors.newSingleThreadExecutor();
//    }
//
//    @Bean
//    HandlerMapping handlerMapping(WebSocketHandler webSocketHandler) {
//        System.out.println("handlerMapping");
//        return new SimpleUrlHandlerMapping() {
//            {
//                setUrlMap(Collections.singletonMap("/ws/profiles", webSocketHandler));
//                setOrder(10);
//            }
//        };
//    }
//
//    @Bean
//    WebSocketHandlerAdapter webSocketHandlerAdapter() {
//        System.out.println("webSocketHandlerAdapter");
//        return new WebSocketHandlerAdapter();
//    }
//
//    @Bean
//    WebSocketHandler webSocketHandler(ObjectMapper objectMapper, ProfileCreatedEventPublisher eventPublisher) {
//        System.out.println("webSocketHandler");
////        Flux<ProfileCreatedEvent> publish = Flux.create(eventPublisher).share();
//        Flux<String> publish = Flux.just("FUCK", "YOU", "BITCH");
//        return session -> {
//            System.out.println("SESSION WS");
//            Flux<WebSocketMessage> messageFlux = publish.map(evt -> {
//                try {
//                    return objectMapper.writeValueAsString(evt);
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
//            }).map(str -> {
//                log.info("sending " + str);
//                return session.textMessage(str);
//            });
//            return session.send(messageFlux);
//        };
//    }


//    @Bean
//    public HandlerMapping handlerMapping() {
//        Map<String, WebSocketHandler> map = new HashMap<>();
//        map.put("/path", new ReactiveWebSocketHandler());
//        int order = -1;
//
//        return new SimpleUrlHandlerMapping(map, order);
//    }
//
//    @Bean
//    public WebSocketHandlerAdapter handlerAdapter() {
//        return new WebSocketHandlerAdapter();
//    }

//    @Bean
//    public WebSocketService webSocketService() {
//        TomcatRequestUpgradeStrategy tomcatRequestUpgradeStrategy = new TomcatRequestUpgradeStrategy();
//        tomcatRequestUpgradeStrategy.setMaxSessionIdleTimeout(10000L);
//        tomcatRequestUpgradeStrategy.setAsyncSendTimeout(10000L);
//        return new HandshakeWebSocketService(tomcatRequestUpgradeStrategy);
//    }

//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        ServerEndpointExporter serverEndpointExporter = new ServerEndpointExporter();
//
//        /**
//         * Add one or more classes annotated with `@ServerEndpoint`.
//         */
//        serverEndpointExporter.setAnnotatedEndpointClasses(WebSocketController.class);
//
//        return serverEndpointExporter;
//    }

//    @Override
//    public WebSocketService getWebSocketService() {
//        TomcatRequestUpgradeStrategy strategy = new TomcatRequestUpgradeStrategy();
//        strategy.setMaxSessionIdleTimeout(0L);
//        return new HandshakeWebSocketService(strategy);
//    }
}
