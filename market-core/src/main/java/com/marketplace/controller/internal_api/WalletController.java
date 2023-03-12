package com.marketplace.controller.internal_api;

import com.marketplace.client.BitcoinCoreClient;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class WalletController {

    @RequestMapping(value = "/createnewadress", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @SneakyThrows
    public @ResponseBody
    String createNewAddress() {

//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(new URI("http://127.0.0.1:8332/"))
//                .header("content-type", "text/plain")
//                .POST(HttpRequest.BodyPublishers.ofString("{\"jsonrpc\": \"1.0\", \"id\":\"curltest\", \"method\": \"getnewaddress\", \"params\": [] }"))
//                .build();
//
//        HttpResponse<String> response = BitcoinCoreClient.httpClient()
//                .send(request, HttpResponse.BodyHandlers.ofString());
//
//        System.out.println(response.version());
//        System.out.println(response.body());
//        return response.body();
        return "{\"dhkvkhsdvbhdsvkdsv\": \"sdvfd\"}";
    }

    @RequestMapping(value = "/getbalance", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    @SneakyThrows
    public @ResponseBody
    String getBalance(@RequestParam String address) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://127.0.0.1:8332/"))
                .header("content-type", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString("{\"jsonrpc\": \"1.0\", \"id\":\"curltest\", \"method\": \"getnewaddress\", \"params\": [] }"))
                .build();

        HttpResponse<String> response = BitcoinCoreClient.httpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.version());
        System.out.println(response.body());
        return response.body();
    }

}
