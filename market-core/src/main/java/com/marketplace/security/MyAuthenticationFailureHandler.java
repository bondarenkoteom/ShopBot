package com.marketplace.security;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.DefaultRedirectStrategy;
//import org.springframework.security.web.RedirectStrategy;
//import org.springframework.security.web.WebAttributes;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;

public class MyAuthenticationFailureHandler {//implements AuthenticationFailureHandler {
//    protected final Log logger = LogFactory.getLog(this.getClass());
//
//    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
//
//    public MyAuthenticationFailureHandler() {
//        super();
//    }
//
//    @Override
//    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException {
//        handle(request, response, exception);
//        clearAuthenticationAttributes(request);
//    }
//
//    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception) throws IOException {
//        String targetUrl = "/login?error";
//
//        if (response.isCommitted()) {
//            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
//            return;
//        }
//
//        Cookie cookie = new Cookie("JSESSIONID", null);
//        cookie.setMaxAge(0);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//
//        redirectStrategy.sendRedirect(request, response, targetUrl);
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
