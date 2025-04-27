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

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String API_KEY = "ac872c9e-191c-11f0-8b17-0200cd936042";
    private static final String VALID_STAFF_CODE = "GEC2025STAFF"; // Staff access code

    public void init() {
        ConnectionFactory.getConnection();
    }

    @SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");
        String otp = request.getParameter("otp");
        String staffCode = request.getParameter("staffCode");

        // Validate staff code
        if (staffCode == null || !staffCode.equals(VALID_STAFF_CODE)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Invalid staff code. Only staff members can register.");
            return;
        }

        // TEMPORARY BYPASS: Disable email domain check for testing
        if (!isStaffEmail(email)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Only staff email addresses are allowed to register.");
            return;
        }

        HttpSession session = request.getSession();
        String sessionId = (String) session.getAttribute("otpSessionId");
        Boolean staffCodeVerified = (Boolean) session.getAttribute("staffCodeVerified");

        if (sessionId == null) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("OTP session expired. Please request a new OTP.");
            return;
        }

        if (staffCodeVerified == null || !staffCodeVerified) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Staff verification failed. Please restart the registration process.");
            return;
        }

        // OTP Verification - Improved Error Handling
        try {
            String verifyUrl = "https://2factor.in/API/V1/" + API_KEY + "/SMS/VERIFY/" + sessionId + "/" + otp;
            URL url = new URL(verifyUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            int status = conn.getResponseCode();
            
            // Check for HTTP error responses
            if (status != 200) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid OTP. Please try again.");
                return;
            }
            
            // Read response only if successful
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            conn.disconnect();

            // Validate response content
            if (!content.toString().contains("\"Status\":\"Success\"")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("OTP verification failed. Please try again.");
                return;
            }
        } catch (IOException e) {
            // Handle specific network errors
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid OTP or verification failed. Please try again.");
            e.printStackTrace();
            return;
        }

        // Validate password strength
        if (!isStrongPassword(password)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Password must be at least 8 characters and include uppercase, lowercase, number, and special character.");
            return;
        }

        // Check if email already exists
        if (isEmailRegistered(email)) {
            response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().write("Email is already registered.");
            return;
        }

        // Register the user
        if (registerUser(email, password, mobile)) {
            session.removeAttribute("otpSessionId");
            session.removeAttribute("staffCodeVerified");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Registration successful!");
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Registration failed due to a system error. Please try again later.");
        }
    }

    // Bypass email check for now
    private boolean isStaffEmail(String email) {
        String[] validDomains = {"gopichandcollege.edu", "staff.gopichand.ac.in", "gopichand.ac.in", "gec.ac.in"};
        for (String domain : validDomains) {
            if (email.toLowerCase().endsWith("@" + domain)) {
                return true;
            }
        }
        return false;
    }

    private boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecial;
    }

    private boolean isEmailRegistered(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("SELECT COUNT(*) FROM USERS WHERE email = ?");
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    private boolean registerUser(String email, String password, String mobile) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement("INSERT INTO USERS (email, password, mobile, NAME, profile_image) VALUES (?, ?, ?, 'GOPICHAND', 'chandu.jpg')");
            stmt.setString(1, email);
            stmt.setString(2, hashPassword(password));
            stmt.setString(3, mobile);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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