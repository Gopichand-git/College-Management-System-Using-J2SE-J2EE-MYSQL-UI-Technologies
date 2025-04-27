package com.gopichand.Validations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/SignupOtpServlet")
public class SignupOtpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "ac872c9e-191c-11f0-8b17-0200cd936042";
    private static final String VALID_STAFF_CODE = "GEC2025STAFF"; // Staff access code

    public void init() {
        ConnectionFactory.getConnection();
    }

    @SuppressWarnings("deprecation")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mobile = request.getParameter("mobile");
        String staffCode = request.getParameter("staffCode");
        
        // Validate staff code first
        if (staffCode == null || !staffCode.equals(VALID_STAFF_CODE)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Invalid staff access code. Only staff members can register.");
            return;
        }
        
        if (mobile == null || mobile.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Mobile number is required");
            return;
        }
        
        try {
            // Format number with country code if needed
            if (!mobile.startsWith("+")) {
                mobile = "+91" + mobile; // Assuming India country code
            }

            String url = "https://2factor.in/API/V1/" + API_KEY + "/SMS/" + mobile + "/AUTOGEN";
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            
            int responseCode = conn.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    (responseCode == 200) ? conn.getInputStream() : conn.getErrorStream()));
            
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            in.close();
            
            if (responseCode == 200) {
                // Extract session_id from response
                String responseJson = result.toString();
                
                // Check if response contains success message
                if (responseJson.contains("\"Status\":\"Success\"")) {
                    // Extract the session ID
                    String sessionId = null;
                    if (responseJson.contains("\"Details\":\"")) {
                        sessionId = responseJson.split("\"Details\":\"")[1].split("\"")[0];
                    }
                    
                    if (sessionId != null) {
                        // Store in session
                        HttpSession session = request.getSession();
                        session.setAttribute("otpSessionId", sessionId);
                        session.setAttribute("staffCodeVerified", true); // Mark staff code as verified
                        
                        response.setStatus(HttpServletResponse.SC_OK);
                        response.getWriter().write("OTP sent successfully");
                    } else {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.getWriter().write("Failed to extract session ID");
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().write("Failed to send OTP: " + responseJson);
                }
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Failed to send OTP: " + result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Failed to send OTP: " + e.getMessage());
        }
    }
}