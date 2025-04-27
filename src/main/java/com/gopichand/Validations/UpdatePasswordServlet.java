package com.gopichand.Validations;

import java.io.*;
import com.gopichand.factory.ConnectionFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.*;

@WebServlet("/UpdatePasswordServlet")
public class UpdatePasswordServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the new password and confirm password from the form
        String newPassword = request.getParameter("newpassword");
        String confirmPassword = request.getParameter("confirmpassword");

        // Database connection parameters
        String email = (String) request.getSession().getAttribute("email");

        if (email == null) {
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Session expired. Please log in again.\"}");
            return;
        }

        // Database connection objects
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        // Validate if the passwords match
        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Passwords do not match!\"}");
            return;
        }

        // Hash the password (you should use a proper hashing method, e.g., BCrypt)
        String hashedPassword = hashPassword(newPassword);

        try {
            // Connect to the database
            connection = ConnectionFactory.getConnection();

            // SQL query to update the user's password
            String updatePasswordSQL = "UPDATE users SET password = ? WHERE email = ?";
            preparedStatement = connection.prepareStatement(updatePasswordSQL);
            preparedStatement.setString(1, hashedPassword); // Set the hashed password
            preparedStatement.setString(2, email);

            // Execute the update query
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Password updated successfully, send success response
                response.setContentType("application/json");
                response.getWriter().write("{\"status\": \"success\", \"message\": \"Password updated successfully!\"}");
            } else {
                // Failed to update password
                response.setContentType("application/json");
                response.getWriter().write("{\"status\": \"error\", \"message\": \"Failed to update password. Please try again later.\"}");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("application/json");
            response.getWriter().write("{\"status\": \"error\", \"message\": \"Database error occurred. Please try again later.\"}");
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to hash the password (use a secure hashing algorithm like BCrypt or SHA-256)
    private String hashPassword(String password) {
        // Ideally, you should hash the password with a secure algorithm
        // For the sake of example, let's assume we use SHA-256 for this implementation
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
