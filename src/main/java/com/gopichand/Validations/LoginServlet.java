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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.gopichand.factory.ConnectionFactory;
import org.json.JSONObject;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "ac872c9e-191c-11f0-8b17-0200cd936042";
    
    public void init() {
        ConnectionFactory.getConnection();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        // Hash the input password
        String hashedInputPassword = hashPassword(password);
        
        try {
            Connection connection = ConnectionFactory.getConnection();
            if (connection == null) {
                System.out.println("Database connection failed");
                response.getWriter().write("fail: Database connection error");
                return;
            }
            
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE email=?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                System.out.println("Stored password: " + storedPassword);
                
                if (storedPassword != null && storedPassword.equals(hashedInputPassword)) {
                    // Valid credentials - send OTP using 2Factor.in
                    String mobile = rs.getString("mobile");
                    String name = rs.getString("name");
                    String imageUrl = rs.getString("profile_image");
                    
                    // Store user details in session
                    HttpSession session = request.getSession();
                    session.setAttribute("email", email);
                    session.setAttribute("name", name);
                    session.setAttribute("imageUrl", imageUrl);
                    session.setAttribute("mobile", mobile);
                    
                    // Send OTP using 2Factor.in API
                    String otpSessionId = sendOTP(mobile);
                    
                    if (otpSessionId != null) {
                        // Store the session ID in session for verification later
                        session.setAttribute("otpSessionId", otpSessionId);
                        
                        // Return success instead of otp:mobile
                        response.getWriter().write("success");
                    } else {
                        response.getWriter().write("fail: Failed to send OTP");
                    }
                } else {
                    System.out.println("Invalid password for: " + email);
                    response.getWriter().write("fail");
                }
            } else {
                System.out.println("No user found with email: " + email);
                response.getWriter().write("fail");
            }
            
            rs.close();
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("fail: " + e.getMessage());
        }
    }
    
    // Method to send OTP via 2Factor.in API
    private String sendOTP(String mobile) {
        try {
            // Format mobile number (ensure it has country code if required by 2Factor.in)
            if (!mobile.startsWith("+91")) {
                mobile = "+91" + mobile;
            }
            
            // Call 2Factor.in API
            String url = "https://2factor.in/API/V1/" + API_KEY + "/SMS/" + mobile + "/AUTOGEN";
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
            
            System.out.println("2Factor API Response: " + result.toString());
            
            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(result.toString());
            
            if ("Success".equals(jsonResponse.getString("Status"))) {
                // Check if Details is a JSONObject or a string
                Object details = jsonResponse.get("Details");
                if (details instanceof JSONObject) {
                    // It's a JSONObject, extract SessionId
                    return ((JSONObject) details).getString("SessionId");
                } else if (details instanceof String) {
                    // It's already a string (might be the session ID directly)
                    return (String) details;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    // SHA-256 hash method
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}