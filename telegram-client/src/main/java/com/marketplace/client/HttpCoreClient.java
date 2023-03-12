package com.marketplace.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Component
public class HttpCoreClient {

    private final HttpCoreInterface httpInterface;

    public HttpCoreClient() {
        WebClient webClient = WebClient.builder().baseUrl("http://localhost:4230/").build();
        WebClientAdapter webClientAdapter = WebClientAdapter.forClient(webClient);

        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(webClientAdapter).build();
        httpInterface = httpServiceProxyFactory.createClient(HttpCoreInterface.class);
    }

    public HttpCoreClient(WebClient webClient) {
        WebClientAdapter webClientAdapter = WebClientAdapter.forClient(webClient);

        HttpServiceProxyFactory httpServiceProxyFactory =
                HttpServiceProxyFactory.builder(webClientAdapter).build();
        httpInterface = httpServiceProxyFactory.createClient(HttpCoreInterface.class);
    }

    public HttpCoreInterface getHttpInterface() {
        return httpInterface;
    }
}
