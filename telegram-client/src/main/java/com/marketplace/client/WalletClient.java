package com.marketplace.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

//@Component
public class WalletClient {

    private final HttpInterface httpInterface;

    public WalletClient(WebClient webClient) {
        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(WebClientAdapter.forClient(webClient))
                        .build();
        httpInterface = httpServiceProxyFactory.createClient(HttpInterface.class);
    }

    public HttpInterface getHttpInterface() {
        return httpInterface;
    }
}
