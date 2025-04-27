package com.gopichand.Validations;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.gopichand.factory.ConnectionFactory;

@WebServlet("/VerifyOtpServlet")
public class VerifyOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "ac872c9e-191c-11f0-8b17-0200cd936042";
    
    public void init() {
        ConnectionFactory.getConnection();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String otp = request.getParameter("otp");
        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("otpSessionId");
        
        if (sessionId == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
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
        
        if (conn.getResponseCode() == 200 && result.toString().contains("\"Status\":\"Success\"")) {
            // OTP verified successfully
            session.setAttribute("otpVerified", true);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            // OTP verification failed
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}

