package com.marketplace.security;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.marketplace.client.BitcoinCoreClient;
//import lombok.SneakyThrows;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.WebAttributes;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Map;

public class MyAuthenticationSuccessHandler {//implements AuthenticationSuccessHandler {
//    protected final Log logger = LogFactory.getLog(this.getClass());
//
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    public MyAuthenticationSuccessHandler() {
//        super();
//    }
//
//    @Override
//    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
//        handle(request, response, authentication);
//        clearAuthenticationAttributes(request);
//    }
//
//    @SneakyThrows
//    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
//        String targetUrl = determineTargetUrl(authentication);
//
//        if (response.isCommitted()) {
//            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
//            return;
//        }
//
////        String token = request.getParameterMap().get("h-captcha-response")[0];
//
////        if (token == null || token.isEmpty()) {
////            targetUrl = "/login?error_captcha";
////            Cookie cookie = new Cookie("JSESSIONID", null);
////            cookie.setMaxAge(0);
////            cookie.setPath("/");
////            response.addCookie(cookie);
////            authentication.setAuthenticated(false);
////            redirectStrategy.sendRedirect(request, response, targetUrl);
////            return;
////        }
//
////        HttpRequest siteVerifyRequest = HttpRequest.newBuilder()
////                .uri(new URI("https://hcaptcha.com/siteverify"))
////                .header("content-type", "application/x-www-form-urlencoded")
////                .POST(HttpRequest.BodyPublishers.ofString("secret=%s&response=%s".formatted("0x9964237A1577A90ddC049Fe15C0FD3D5532D4b02", token)))
////                .build();
//
//        //{"success":true,"challenge_ts":"2023-03-04T21:27:10.000000Z","hostname":"mydomain.com","credit":false}
////        HttpResponse<String> siteVerifyResponse = HttpClient.newBuilder().build()
////                .send(siteVerifyRequest, HttpResponse.BodyHandlers.ofString());
////
////        if (!Boolean.parseBoolean(new ObjectMapper().readTree(siteVerifyResponse.body()).get("success").asText())) {
////            targetUrl = "/login?error_captcha";
////            Cookie cookie = new Cookie("JSESSIONID", null);
////            cookie.setMaxAge(0);
////            cookie.setPath("/");
////            response.addCookie(cookie);
////            authentication.setAuthenticated(false);
////        }
//
//        redirectStrategy.sendRedirect(request, response, targetUrl);
//    }
//
//    protected String determineTargetUrl(final Authentication authentication) {
//
//        Map<String, String> roleTargetUrlMap = new HashMap<>();
//        roleTargetUrlMap.put("ROLE_USER", "/logout");
//        roleTargetUrlMap.put("ROLE_ADMIN", "/dashboard");
//
//        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        for (final GrantedAuthority grantedAuthority : authorities) {
//
//            String authorityName = grantedAuthority.getAuthority();
//            if(roleTargetUrlMap.containsKey(authorityName)) {
//                return roleTargetUrlMap.get(authorityName);
//            }
//        }
//
//        throw new IllegalStateException();
//    }
//
//    /**
//     * Removes temporary authentication-related data which may have been stored in the session
//     * during the authentication process.
//     */
//    protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
//        final HttpSession session = request.getSession(false);
//
//        if (session == null) {
//            return;
//        }
//
//        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//    }

}
