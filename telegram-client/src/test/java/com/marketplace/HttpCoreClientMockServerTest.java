package com.marketplace;

import com.marketplace.client.HttpCoreClient;
import com.marketplace.client.HttpCoreInterface;
import com.marketplace.constant.Category;
import com.marketplace.constant.OrderStatus;
import com.marketplace.constant.ProductStatus;
import com.marketplace.constant.Trigger;
import com.marketplace.entity.*;
import com.marketplace.requests.*;
import com.marketplace.responses.BuyResponse;
import com.marketplace.responses.TriggerResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.configuration.Configuration;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.MediaType;
import org.mockserver.verify.VerificationTimes;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

class HttpCoreClientMockServerTest {

    private static final String SERVER_ADDRESS = "localhost";
    private static int serverPort;
    private static String serviceUrl;

    private static ClientAndServer mockServer;
    private static HttpCoreInterface httpCoreInterface;

    @BeforeAll
    static void startServer() throws IOException {
        serverPort = getFreePort();
        serviceUrl = "http://" + SERVER_ADDRESS + ":" + serverPort;

        Configuration config = Configuration.configuration();
        mockServer = startClientAndServer(config, serverPort);

        HttpCoreClient httpCoreClient = new HttpCoreClient(WebClient.builder().baseUrl(serviceUrl).build());
        httpCoreInterface = httpCoreClient.getHttpInterface();
    }

    @AfterAll
    static void stopServer() {
        mockServer.stop();
    }

    @Test
    void searchPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/product/search")
                                .withQueryStringParameter("page", "0")
                                .withQueryStringParameter("size", "1")
                                .withMethod(HttpMethod.POST.name())
                                .withBody("{\"query\":\"1\",\"category\":\"ALL\"}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"totalPages\":1,\"totalElements\":1,\"size\":1,\"content\":[{\"id\":0,\"ownerId\":0,\"status\":\"ACTIVE\",\"ratingGood\":0,\"ratingBad\":0,\"productName\":\"string\",\"instruction\":\"string\",\"description\":\"string\",\"price\":0,\"category\":\"PS_5_KEYS\",\"imageId\":\"string\",\"items\":[\"string\"],\"isEditing\":true}],\"number\":0,\"sort\":{\"empty\":true,\"sorted\":true,\"unsorted\":true},\"pageable\":{\"offset\":0,\"sort\":{\"empty\":true,\"sorted\":true,\"unsorted\":true},\"pageNumber\":0,\"pageSize\":0,\"paged\":true,\"unpaged\":true},\"first\":true,\"last\":true,\"numberOfElements\":0,\"empty\":true}")
                );

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.setQuery("1");
        searchRequest.setCategory(Category.ALL);

        Page<Product> products = httpCoreInterface.search(0, 1, null, searchRequest);
        assertEquals(1, products.getSize());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.POST.name())
                        .withPath("/api/v1/product/search"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void buyPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/product/buy")
                                .withMethod(HttpMethod.POST.name())
                                .withBody("{\"productId\":1,\"userId\":1}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"error\":true,\"message\":\"string\",\"purchase\":{\"id\":0,\"price\":0,\"item\":\"string\",\"name\":\"string\",\"instruction\":\"string\",\"productId\":0,\"status\":\"CONFIRMED\",\"buyer\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"seller\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T13:02:06.539Z\"},\"balance\":0}")
                );

        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setProductId(1L);
        buyRequest.setUserId(1L);

        BuyResponse buyResponse = httpCoreInterface.buy(buyRequest);
        assertEquals(true, buyResponse.getPurchase().isPresent());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.POST.name())
                        .withPath("/api/v1/product/buy"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void purchasesPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/purchases")
                                .withQueryStringParameter("page", "0")
                                .withQueryStringParameter("size", "1")
                                .withMethod(HttpMethod.POST.name())
                                .withBody("{\"buyerId\":1}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"totalPages\":1,\"totalElements\":1,\"size\":1,\"content\":[{\"id\":0,\"price\":0,\"item\":\"string\",\"name\":\"string\",\"instruction\":\"string\",\"productId\":0,\"status\":\"CONFIRMED\",\"buyer\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"seller\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T13:13:01.477Z\"}],\"number\":0,\"sort\":{\"empty\":true,\"sorted\":true,\"unsorted\":true},\"pageable\":{\"offset\":0,\"sort\":{\"empty\":true,\"sorted\":true,\"unsorted\":true},\"pageNumber\":0,\"pageSize\":0,\"paged\":true,\"unpaged\":true},\"first\":true,\"last\":true,\"numberOfElements\":0,\"empty\":true}")
                );

        PurchaseRequest purchaseRequest = new PurchaseRequest();
        purchaseRequest.setBuyerId(1L);

        Page<Purchase> products = httpCoreInterface.purchases(0, 1, new String[]{}, purchaseRequest);
        assertEquals(1, products.getSize());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.POST.name())
                        .withPath("/api/v1/purchases"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void productGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/product")
                                .withQueryStringParameter("productId", "1")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"error\":true,\"message\":\"string\",\"purchase\":{\"id\":0,\"price\":0,\"item\":\"string\",\"name\":\"string\",\"instruction\":\"string\",\"productId\":0,\"status\":\"CONFIRMED\",\"buyer\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"seller\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T13:02:06.539Z\"},\"balance\":0}")
                );

        Optional<Product> optionalProduct = httpCoreInterface.productGet(1L);
        assertEquals(true, optionalProduct.isPresent());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/product"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void productUpdatePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/product")
                                .withMethod(HttpMethod.PUT.name())
                                .withBody("{\"id\":0,\"ownerId\":0,\"status\":\"ACTIVE\",\"ratingGood\":0,\"ratingBad\":0,\"productName\":\"string\",\"instruction\":\"string\",\"description\":\"string\",\"price\":0.0,\"category\":\"PS_5_KEYS\",\"imageId\":\"string\",\"items\":[\"string\"],\"isEditing\":true}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                );

        Product product = new Product();
        product.setId(0L);
        product.setOwnerId(0L);
        product.setStatus(ProductStatus.ACTIVE);
        product.setRatingGood(0);
        product.setRatingBad(0);
        product.setProductName("string");
        product.setInstruction("string");
        product.setDescription("string");
        product.setPrice(0.0);
        product.setCategory(Category.PS_5_KEYS);
        product.setImageId("string");
        product.setItems(new String[]{"string"});
        product.setIsEditing(true);

        ResponseEntity<Void> response = httpCoreInterface.productUpdate(product);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.PUT.name())
                        .withPath("/api/v1/product"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void productDeletePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/product")
                                .withQueryStringParameter("productId", "1")
                                .withMethod(HttpMethod.DELETE.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                );

        ResponseEntity<Void> response = httpCoreInterface.productDelete(1L);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.DELETE.name())
                        .withPath("/api/v1/product"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void productsPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/products")
                                .withQueryStringParameter("page", "0")
                                .withQueryStringParameter("size", "1")
                                .withMethod(HttpMethod.POST.name())
                                .withBody("{\"sellerId\":1,\"isEditing\":true}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"totalPages\":1,\"totalElements\":1,\"size\":1,\"content\":[{\"id\":0,\"ownerId\":0,\"status\":\"ACTIVE\",\"ratingGood\":0,\"ratingBad\":0,\"productName\":\"string\",\"instruction\":\"string\",\"description\":\"string\",\"price\":0,\"category\":\"PS_5_KEYS\",\"imageId\":\"string\",\"items\":[\"string\"],\"isEditing\":true}],\"number\":0,\"sort\":{\"empty\":true,\"sorted\":true,\"unsorted\":true},\"pageable\":{\"offset\":0,\"sort\":{\"empty\":true,\"sorted\":true,\"unsorted\":true},\"pageNumber\":0,\"pageSize\":0,\"paged\":true,\"unpaged\":true},\"first\":true,\"last\":true,\"numberOfElements\":0,\"empty\":true}")
                );

        ProductRequest productRequest = new ProductRequest();
        productRequest.setSellerId(1L);
        productRequest.setIsEditing(true);

        Page<Product> products = httpCoreInterface.products(0, 1, null, productRequest);
        assertEquals(1, products.getSize());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.POST.name())
                        .withPath("/api/v1/products"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void productEditingGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/product/editing")
                                .withQueryStringParameter("ownerId", "2")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"id\":0,\"ownerId\":0,\"status\":\"ACTIVE\",\"ratingGood\":0,\"ratingBad\":0,\"productName\":\"string\",\"instruction\":\"string\",\"description\":\"string\",\"price\":0,\"category\":\"PS_5_KEYS\",\"imageId\":\"string\",\"items\":[\"string\"],\"isEditing\":true}")
                );

        Optional<Product> optionalProduct = httpCoreInterface.productEditingGet(2L);
        assertEquals(true, optionalProduct.isPresent());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/product/editing"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void productEditingDeletePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/product/editing")
                                .withQueryStringParameter("ownerId", "1")
                                .withMethod(HttpMethod.DELETE.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                );

        ResponseEntity<Void> response = httpCoreInterface.productEditingDelete(1L);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.DELETE.name())
                        .withPath("/api/v1/product/editing"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void purchaseGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/purchase")
                                .withQueryStringParameter("purchaseId", "0")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"id\":0,\"price\":0,\"item\":\"string\",\"name\":\"string\",\"instruction\":\"string\",\"productId\":0,\"status\":\"CONFIRMED\",\"buyer\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"seller\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T14:10:11.757Z\"}")
                );

        BuyRequest buyRequest = new BuyRequest();
        buyRequest.setProductId(1L);
        buyRequest.setUserId(1L);

        Optional<Purchase> optionalPurchase = httpCoreInterface.purchaseGet(0L);
        assertEquals(true, optionalPurchase.isPresent());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/purchase"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void statusUpdatePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/purchase/status")
                                .withQueryStringParameter("purchaseId", "1")
                                .withQueryStringParameter("status", "CONFIRMED")
                                .withMethod(HttpMethod.PUT.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                );

        ResponseEntity<Void> response = httpCoreInterface.statusUpdate(1L, OrderStatus.CONFIRMED);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.PUT.name())
                        .withPath("/api/v1/purchase/status"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void userGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/user")
                                .withQueryStringParameter("userId", "1")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"error\":true,\"message\":\"string\",\"purchase\":{\"id\":0,\"price\":0,\"item\":\"string\",\"name\":\"string\",\"instruction\":\"string\",\"productId\":0,\"status\":\"CONFIRMED\",\"buyer\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"seller\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T13:02:06.539Z\"},\"balance\":0}")
                );

        Optional<User> optionalUser = httpCoreInterface.userGet(1L, null);
        assertEquals(true, optionalUser.isPresent());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/user"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void userCreatePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/user")
                                .withMethod(HttpMethod.POST.name())
                                .withBody("{\"userId\":3,\"username\":\"test\"}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                );

        UserRequest userRequest = new UserRequest();
        userRequest.setUserId(3L);
        userRequest.setUsername("test");

        ResponseEntity<Void> response = httpCoreInterface.userCreate(userRequest);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.POST.name())
                        .withPath("/api/v1/user"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void userUpdatePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/user")
                                .withMethod(HttpMethod.PUT.name())
                                .withBody("{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0.0,\"sells\":0,\"purchases\":0}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                );

        User user = new User();
        user.setId(0L);
        user.setUsername("string");
        user.setRating(0);
        user.setWaitFor(Trigger.UNDEFINED);
        user.setBalance(0.0);
        user.setSells(0);
        user.setPurchases(0);

        ResponseEntity<Void> response = httpCoreInterface.userUpdate(user);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.PUT.name())
                        .withPath("/api/v1/user"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void triggerGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/user/trigger")
                                .withQueryStringParameter("userId", "1")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"trigger\":\"UNDEFINED\"}")
                );

        TriggerResponse triggerResponse = httpCoreInterface.triggerGet(1L);
        assertEquals(Trigger.UNDEFINED, triggerResponse.getTrigger());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/user/trigger"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void triggerUpdatePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/user/trigger")
                                .withMethod(HttpMethod.PUT.name())
                                .withBody("{\"userId\":0,\"trigger\":\"UNDEFINED\"}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"error\":true,\"message\":\"string\",\"purchase\":{\"id\":0,\"price\":0,\"item\":\"string\",\"name\":\"string\",\"instruction\":\"string\",\"productId\":0,\"status\":\"CONFIRMED\",\"buyer\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"seller\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T13:02:06.539Z\"},\"balance\":0}")
                );

        TriggerRequest triggerRequest = new TriggerRequest();
        triggerRequest.setUserId(0L);
        triggerRequest.setTrigger(Trigger.UNDEFINED);

        ResponseEntity<Void> response = httpCoreInterface.triggerUpdate(triggerRequest);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.PUT.name())
                        .withPath("/api/v1/user/trigger"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void topUsersPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/users/top")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("[{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0}]")
                );

        List<User> userList = httpCoreInterface.topUsers();
        assertEquals(1, userList.size());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/users/top"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void disputesGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/disputes")
                                .withQueryStringParameter("purchaseId", "1")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("[{\"id\":0,\"text\":\"string\",\"sender\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T14:55:52.099Z\",\"purchaseId\":0}]")
                );

        List<Dispute> disputeList = httpCoreInterface.disputesGet(1L);
        assertEquals(1, disputeList.size());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/disputes"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void disputesPurchasesGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/disputes/purchases")
                                .withQueryStringParameter("sellerId", "1")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("[{\"id\":0,\"price\":0,\"item\":\"string\",\"name\":\"string\",\"instruction\":\"string\",\"productId\":0,\"status\":\"CONFIRMED\",\"buyer\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"seller\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T14:58:29.314Z\"}]")
                );

        List<Purchase>  purchasesList = httpCoreInterface.disputesPurchasesGet(1L, null);
        assertEquals(1, purchasesList.size());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/disputes/purchases"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void disputeCreatePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/dispute")
                                .withMethod(HttpMethod.POST.name())
                                .withBody("{\"id\":0,\"text\":\"string\",\"sender\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0.0,\"sells\":0,\"purchases\":0},\"date\":null,\"purchaseId\":0}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                );

        User user = new User();
        user.setId(0L);
        user.setUsername("string");
        user.setRating(0);
        user.setWaitFor(Trigger.UNDEFINED);
        user.setBalance(0.0);
        user.setSells(0);
        user.setPurchases(0);

        Dispute dispute = new Dispute();
        dispute.setId(0L);
        dispute.setText("string");
        dispute.setSenderId(user.getId());
        dispute.setPurchaseId(0L);

        ResponseEntity<Void> response = httpCoreInterface.disputeCreate(dispute);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.POST.name())
                        .withPath("/api/v1/dispute"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void messagesGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/messages")
                                .withQueryStringParameter("page", "0")
                                .withQueryStringParameter("size", "1")
                                .withQueryStringParameter("superUserId", "1")
                                .withQueryStringParameter("userId", "2")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("{\"totalPages\":1,\"totalElements\":1,\"size\":1,\"content\":[{\"id\":0,\"text\":\"string\",\"sender\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"receiver\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0},\"date\":\"2023-03-12T15:06:29.019Z\"}],\"number\":0,\"sort\":{\"empty\":true,\"sorted\":true,\"unsorted\":true},\"pageable\":{\"offset\":0,\"sort\":{\"empty\":true,\"sorted\":true,\"unsorted\":true},\"pageNumber\":0,\"pageSize\":0,\"paged\":true,\"unpaged\":true},\"first\":true,\"last\":true,\"numberOfElements\":0,\"empty\":true}")
                );

        Page<Message> messages = httpCoreInterface.messagesGet(0, 1, new String[]{}, 1L, 2L);
        assertEquals(1, messages.getSize());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/messages"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void messagesUsersGetPositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/messages/users")
                                .withQueryStringParameter("userId", "1")
                                .withMethod(HttpMethod.GET.name()),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                                .withContentType(MediaType.APPLICATION_JSON)
                                .withBody("[{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0,\"sells\":0,\"purchases\":0}]")
                );

        List<User> userList = httpCoreInterface.messagesUsersGet(1L);
        assertEquals(1, userList.size());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.GET.name())
                        .withPath("/api/v1/messages/users"),
                VerificationTimes.exactly(1)
        );
    }

    @Test
    void messageCreatePositiveTest() {
        new MockServerClient(SERVER_ADDRESS, serverPort)
                .when(
                        request()
                                .withPath("/api/v1/message")
                                .withMethod(HttpMethod.POST.name())
                                .withBody("{\"id\":0,\"text\":\"string\",\"sender\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0.0,\"sells\":0,\"purchases\":0},\"receiver\":{\"id\":0,\"username\":\"string\",\"rating\":0,\"waitFor\":\"UNDEFINED\",\"balance\":0.0,\"sells\":0,\"purchases\":0},\"date\":null}"),
                        exactly(1)
                )
                .respond(
                        response()
                                .withStatusCode(HttpStatus.SC_OK)
                );

        User sender = new User();
        sender.setId(0L);
        sender.setUsername("string");
        sender.setRating(0);
        sender.setWaitFor(Trigger.UNDEFINED);
        sender.setBalance(0.0);
        sender.setSells(0);
        sender.setPurchases(0);

        User receiver = new User();
        receiver.setId(0L);
        receiver.setUsername("string");
        receiver.setRating(0);
        receiver.setWaitFor(Trigger.UNDEFINED);
        receiver.setBalance(0.0);
        receiver.setSells(0);
        receiver.setPurchases(0);

        Message message = new Message();
        message.setId(0L);
        message.setText("string");
        message.setSenderId(sender.getId());
        message.setReceiverId(receiver.getId());

        ResponseEntity<Void> response = httpCoreInterface.messageCreate(message);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        mockServer.verify(
                HttpRequest.request()
                        .withMethod(HttpMethod.POST.name())
                        .withPath("/api/v1/message"),
                VerificationTimes.exactly(1)
        );
    }

    private static int getFreePort() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(0)) {
            return serverSocket.getLocalPort();
        }
    }

}
