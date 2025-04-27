package com.gopichand.FeePayment;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PaymentHistoryServlet")
public class PaymentHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String sid = request.getParameter("sid");
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        
        // Validate student ID parameter
        if (sid == null || sid.trim().isEmpty()) {
            result.put("status", "error");
            result.put("message", "Student ID is required");
            out.print(result.toString());
            out.flush();
            return;
        }
        
        try {
            // Database connection
            Connection con = ConnectionFactory.getConnection();
            
            // First, check if student exists
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM student WHERE sid = ?");
            ps1.setString(1, sid);
            ResultSet rs1 = ps1.executeQuery();
            
            if (!rs1.next()) {
                result.put("status", "error");
                result.put("message", "Student ID not found");
                out.print(result.toString());
                out.flush();
                return;
            }
            
            // Get student details
            String firstName = rs1.getString("firstname");
            String lastName = rs1.getString("lastname");
            String course = rs1.getString("course");
            String branch = rs1.getString("branch");
            String section = rs1.getString("section");
            int totalFee = rs1.getInt("fee");
            
            // Get payment history
            PreparedStatement ps2 = con.prepareStatement(
                "SELECT payment_method, upi_ref, paid_fee, payment_date " +
                "FROM payments " +
                "WHERE sid = ? " +
                "ORDER BY payment_date DESC"
            );
            ps2.setString(1, sid);
            ResultSet rs2 = ps2.executeQuery();
            
            JSONArray paymentHistory = new JSONArray();
            int totalPaid = 0;
            
            while (rs2.next()) {
                JSONObject payment = new JSONObject();
                int paidAmount = rs2.getInt("paid_fee");
                totalPaid += paidAmount;
                
                payment.put("method", rs2.getString("payment_method"));
                payment.put("upiRef", rs2.getString("upi_ref") != null ? rs2.getString("upi_ref") : "N/A");
                payment.put("amount", paidAmount);
                payment.put("date", rs2.getTimestamp("payment_date").toString());
                
                paymentHistory.put(payment);
            }
            
            // Calculate due amount
            int dueAmount = totalFee - totalPaid;
            
            // Build response
            result.put("status", "success");
            result.put("studentInfo", new JSONObject()
                .put("sid", sid)
                .put("name", firstName + " " + lastName)
                .put("course", course)
                .put("branch", branch)
                .put("section", section)
                .put("totalFee", totalFee)
                .put("totalPaid", totalPaid)
                .put("dueAmount", dueAmount)
            );
            result.put("paymentHistory", paymentHistory);
            
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            result.put("status", "error");
            result.put("message", "Server error: " + e.getMessage());
        }
        
        out.print(result.toString());
        out.flush();
    }
}
