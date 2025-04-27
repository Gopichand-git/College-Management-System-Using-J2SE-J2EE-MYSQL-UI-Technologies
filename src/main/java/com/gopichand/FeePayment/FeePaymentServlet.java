package com.gopichand.FeePayment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;

import org.json.JSONObject;

import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FeePaymentServlet")
public class FeePaymentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("FeePaymentServlet - doPost method called at: " + java.time.LocalDateTime.now());
        
        String sid = request.getParameter("sid");
        String paidFeeStr = request.getParameter("paidFee");
        String method = request.getParameter("method");
        String upiRef = request.getParameter("upiRef");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        try {
            // Validate paid fee amount
            int paidFee = Integer.parseInt(paidFeeStr);
            if (paidFee <= 0) {
                json.put("status", "error");
                json.put("message", "Payment amount must be greater than 0");
                out.print(json.toString());
                out.flush();
                return;
            }

            LocalDate date = LocalDate.now();

            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = ConnectionFactory.getConnection();

            // Get student details
            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM student WHERE sid = ?");
            ps1.setString(1, sid);
            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                String course = rs.getString("course");
                String branch = rs.getString("branch");
                String section = rs.getString("section");
                int totalFee = rs.getInt("fee");

                // Check if payment already exists
                PreparedStatement ps2 = con.prepareStatement("SELECT paid_fee FROM payments WHERE sid = ?");
                ps2.setString(1, sid);
                ResultSet rs2 = ps2.executeQuery();

                int alreadyPaidFee = 0;
                if (rs2.next()) {
                    alreadyPaidFee = rs2.getInt("paid_fee");
                }

                int newPaidFee = alreadyPaidFee + paidFee;

                // Check if student is trying to pay more than due amount
                if (newPaidFee > totalFee) {
                    json.put("status", "error");
                    json.put("message", "No due amount left. Total fee is ₹" + totalFee + " Already paid No Due ₹" + alreadyPaidFee);
                    out.print(json.toString());
                    out.flush();
                    return;
                }

                // Process payment
                if (alreadyPaidFee > 0) {
                    PreparedStatement ps3 = con.prepareStatement(
                        "UPDATE payments SET paid_fee=?, payment_method=?, upi_ref=?, payment_date=? WHERE sid=?");
                    ps3.setInt(1, newPaidFee);
                    ps3.setString(2, method);
                    ps3.setString(3, upiRef);
                    ps3.setDate(4, Date.valueOf(date));
                    ps3.setString(5, sid);
                    ps3.executeUpdate();
                } else {
                    PreparedStatement ps4 = con.prepareStatement(
                        "INSERT INTO payments(sid, paid_fee, payment_method, upi_ref, payment_date) VALUES(?,?,?,?,?)");
                    ps4.setString(1, sid);
                    ps4.setInt(2, paidFee);
                    ps4.setString(3, method);
                    ps4.setString(4, upiRef);
                    ps4.setDate(5, Date.valueOf(date));
                    ps4.executeUpdate();
                }

                System.out.println("FeePaymentServlet - Before inserting into transaction_history for SID: " + sid + ", Amount: " + paidFee + ", Method: " + method + " at: " + java.time.LocalDateTime.now());

                // Before inserting into transaction_history, check if the transaction already exists
                PreparedStatement ps6 = con.prepareStatement(
                    "SELECT * FROM transaction_history WHERE sid = ? AND payment_date = ? AND payment_method = ?");
                ps6.setString(1, sid);
                ps6.setDate(2, Date.valueOf(date));
                ps6.setString(3, method);
                ResultSet rs6 = ps6.executeQuery();

                if (!rs6.next()) {
                    // Insert into transaction_history only if the transaction doesn't exist already
                    PreparedStatement ps5 = con.prepareStatement(
                        "INSERT INTO transaction_history(sid, amount, payment_method, upi_ref, payment_date) VALUES(?,?,?,?,?)");
                    ps5.setString(1, sid);
                    ps5.setInt(2, paidFee);
                    ps5.setString(3, method);
                    ps5.setString(4, upiRef);
                    ps5.setDate(5, Date.valueOf(date));
                    ps5.executeUpdate();
                    System.out.println("FeePaymentServlet - After inserting into transaction_history for SID: " + sid + " at: " + java.time.LocalDateTime.now());
                } else {
                    System.out.println("Transaction already exists in transaction_history for SID: " + sid + " on " + date);
                }

                int dueFee = totalFee - newPaidFee;

                // Success JSON
                json.put("status", "success");
                json.put("sid", sid);
                json.put("name", fname + " " + lname);
                json.put("course", course);
                json.put("branch", branch);
                json.put("section", section);
                json.put("totalFee", totalFee);
                json.put("todayPaid", paidFee); // Today's payment amount
                json.put("paidFee", newPaidFee); // Total paid fee
                json.put("dueFee", dueFee);
                json.put("method", method);
                json.put("upiRef", upiRef != null ? upiRef : "N/A");
                json.put("date", date.toString());

            } else {
                // Student ID not found error
                json.put("status", "error");
                json.put("message", "Student ID not found");
            }

            con.close();

        } catch (NumberFormatException e) {
            json.put("status", "error");
            json.put("message", "Invalid payment amount");
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "Server Error: " + e.getMessage());
        }

        out.print(json.toString());
        out.flush();
    }
}
