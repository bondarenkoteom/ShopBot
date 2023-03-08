package com.marketplace.servlet;


//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import java.io.IOException;

//@WebServlet("/captcha-servlet")
public class CaptchaServlet { //extends HttpServlet {

//	private static final long serialVersionUID = -7846561487931381593L;
//
//	@Override
//	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		YCage yCage = new YCage();
//
//		response.setContentType("image/"+yCage.getFormat());
//		response.setHeader("Cache-Control", "no-cache");
//		response.setDateHeader("Expires", 0);
//		response.setHeader("Progma", "no-cache");
//		response.setDateHeader("Max-Age", 0);
//
//		String token = yCage.getTokenGenerator().next();
//
//		HttpSession session = request.getSession();
//		session.setAttribute("captcha", token.substring(0, 5));
//		yCage.draw(token.substring(0, 5), response.getOutputStream());
//	}
	
}
