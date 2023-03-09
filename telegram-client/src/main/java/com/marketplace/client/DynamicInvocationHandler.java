package com.marketplace.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DynamicInvocationHandler implements InvocationHandler {

    private String url;
    private HttpClient httpClient;

    public DynamicInvocationHandler(String url) {
        this.httpClient = HttpClient.newHttpClient();
        this.url = url;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("hashCode")) {
            return method.invoke(method, args);
        }

        HttpRequest.Builder builder = HttpRequest.newBuilder();

        if (method.getAnnotation(Path.class) != null) {
            url += method.getAnnotation(Path.class).value();
        }

        if (method.getAnnotation(GET.class) != null) {
            builder.GET();
        } else if (method.getAnnotation(POST.class) != null) {
            builder.POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(args[0])));
        } else if (method.getAnnotation(DELETE.class) != null) {
            builder.DELETE();
        } else {
            builder.PUT(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(args[0])));
        }

        if (args.length > 0 && method.getParameters()[0].getAnnotation(QueryParam.class) != null) {
            url += "?" + method.getParameters()[0].getName() + "=" + args[0];
        }

        HttpRequest httpRequest = builder.uri(new URL(url).toURI()).build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.statusCode());
        System.out.println(response.body());
        System.out.println(response.version());
        return new ObjectMapper().readValue(response.body(), method.getReturnType());
    }
}
