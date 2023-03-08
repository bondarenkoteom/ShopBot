package com.marketplace.client;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.http.HttpClient;

public class BitcoinCoreClient {

    public static HttpClient httpClient() {
        return HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("user", "Kill".toCharArray());
                    }
                })
                .version(HttpClient.Version.HTTP_2)
                .build();
    }
}
