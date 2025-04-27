package com.gopichand.Validations;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        
	        	response.setContentType("text/html");
	        	HttpSession session = request.getSession(false); // Only get session if it exists
	            if (session != null) {
	                session.invalidate(); // Destroy session
	            }
	            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	            response.setHeader("Pragma", "no-cache");
	            response.setDateHeader("Expires", 0);
	            response.sendRedirect("loginform.html");
	        }
}
