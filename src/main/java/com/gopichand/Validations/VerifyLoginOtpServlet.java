package com.gopichand.Validations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/VerifyLoginOtpServlet")
public class VerifyLoginOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "ac872c9e-191c-11f0-8b17-0200cd936042";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otp = request.getParameter("otp");
//        String email = request.getParameter("email"); // Make sure you're getting the email
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("otpSessionId");
        
        response.setContentType("text/plain");
        
        if (sessionId == null) {
            response.getWriter().write("expired");
            return;
        }
        
        try {
            String url = "https://2factor.in/API/V1/" + API_KEY + "/SMS/VERIFY/" + sessionId + "/" + otp;
            @SuppressWarnings("deprecation")
			HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            
            // Log the response for debugging
            System.out.println("2Factor API response: " + result.toString());
            
            if (conn.getResponseCode() == 200 && result.toString().contains("\"Status\":\"Success\"")) {
                // OTP verified successfully
                session.setAttribute("otpVerified", true);
                response.getWriter().write("success");
            } else {
                // OTP verification failed
                response.getWriter().write("invalid");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("error");
        }
    }
}