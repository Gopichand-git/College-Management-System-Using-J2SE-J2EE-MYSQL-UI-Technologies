package com.gopichand.Validations;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import com.gopichand.factory.ConnectionFactory;

@WebServlet("/Reset")
public class ResetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() {
        ConnectionFactory.getConnection();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        HttpSession session = request.getSession();

        String email = request.getParameter("email");
        String newPassword = request.getParameter("newpassword");
        String mobile = request.getParameter("mobile");

        // Check if OTP is verified
        Boolean otpVerified = (Boolean) session.getAttribute("otpVerified");

        if (otpVerified != null && otpVerified) {
            try {
                // Hash the new password before saving to DB
                String hashedPassword = hashPassword(newPassword);

                Connection connection = ConnectionFactory.getConnection();
                PreparedStatement ps = connection.prepareStatement("UPDATE users SET password=? WHERE email=? AND mobile=?");
                ps.setString(1, hashedPassword);
                ps.setString(2, email);
                ps.setString(3, mobile);

                int rowCount = ps.executeUpdate();

                // Clear OTP session data
                session.removeAttribute("otpSessionId");
                session.removeAttribute("otpVerified");

                if (rowCount == 1) {
                    response.sendRedirect("loginform.html?resetSuccess=true");
                } else {
                    response.sendRedirect("reset.html?error=updatefailed");
                }
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("reset.html?error=server");
            }
        } else {
            response.sendRedirect("reset.html?error=notverified");
        }
    }

    // Password hashing method (same as LoginServlet)
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
