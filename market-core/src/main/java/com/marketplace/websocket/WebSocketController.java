package com.marketplace.websocket;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import jakarta.websocket.*;
//import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//@ServerEndpoint("/event-emitter")
public class WebSocketController {

//    @Autowired
//    ApplicationContext context;
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketController.class);
//    @OnOpen
//    public void onOpen(Session session, EndpointConfig endpointConfig) throws IOException {
//        // Get session and WebSocket connection
//        session.setMaxIdleTimeout(0);
//        LOGGER.info("Get session and WebSocket connection");
//    }
//
//    @OnMessage
//    public void onMessage(String message, Session session) throws IOException {
//        // Handle new messages
//        LOGGER.info("Handle new messages -> {}", message );
//    }
//
//    @OnClose
//    public void onClose(Session session) throws IOException {
//        // WebSocket connection closes
//        LOGGER.info("WebSocket connection closes");
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        // Do error handling here
//        LOGGER.info("Do error handling here");
//    }
}