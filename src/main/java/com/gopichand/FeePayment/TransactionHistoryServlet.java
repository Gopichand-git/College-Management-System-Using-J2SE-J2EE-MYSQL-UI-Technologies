package com.gopichand.FeePayment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.time.LocalDate;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TransactionHistoryServlet")
public class TransactionHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sid = request.getParameter("sid");
        String name = request.getParameter("name");
        String fromDateStr = request.getParameter("fromDate");
        String toDateStr = request.getParameter("toDate");

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();

        try {
            // Validate dates
            LocalDate fromDate = fromDateStr != null && !fromDateStr.isEmpty() 
                                ? LocalDate.parse(fromDateStr) 
                                : LocalDate.now().minusMonths(1);  // Default to last month
            
            LocalDate toDate = toDateStr != null && !toDateStr.isEmpty() 
                              ? LocalDate.parse(toDateStr) 
                              : LocalDate.now();  // Default to today
            
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = ConnectionFactory.getConnection();
            
            // Get student information if SID is provided
            if (sid != null && !sid.trim().isEmpty()) {
                PreparedStatement psStudent = con.prepareStatement("SELECT * FROM student WHERE sid = ?");
                psStudent.setString(1, sid);
                ResultSet rsStudent = psStudent.executeQuery();
                
                if (rsStudent.next()) {
                    String fname = rsStudent.getString("firstname");
                    String lname = rsStudent.getString("lastname");
                    String course = rsStudent.getString("course");
                    String branch = rsStudent.getString("branch");
                    String section = rsStudent.getString("section");
                    int totalFee = rsStudent.getInt("fee");
                    
                    // Get paid fee amount
                    PreparedStatement psPaid = con.prepareStatement("SELECT paid_fee FROM payments WHERE sid = ? ORDER BY payment_date DESC LIMIT 1");
                    psPaid.setString(1, sid);
                    ResultSet rsPaid = psPaid.executeQuery();
                    
                    int paidFee = 0;
                    if (rsPaid.next()) {
                        paidFee = rsPaid.getInt("paid_fee");
                    }
                    
                    int dueFee = totalFee - paidFee;
                    
                    // Add student info to response
                    JSONObject studentInfo = new JSONObject();
                    studentInfo.put("sid", sid);
                    studentInfo.put("name", fname + " " + lname);
                    studentInfo.put("course", course);
                    studentInfo.put("branch", branch);
                    studentInfo.put("section", section);
                    studentInfo.put("totalFee", totalFee);
                    studentInfo.put("paidFee", paidFee);
                    studentInfo.put("dueFee", dueFee);
                    
                    json.put("studentInfo", studentInfo);
                }
            }
            
            // Build the SQL query for transactions
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("SELECT t.*, CONCAT(s.firstname, ' ', s.lastname) AS student_name ");
            sqlBuilder.append("FROM transaction_history t ");
            sqlBuilder.append("JOIN student s ON t.sid = s.sid ");
            sqlBuilder.append("WHERE t.payment_date BETWEEN ? AND ? ");
            
            if (sid != null && !sid.trim().isEmpty()) {
                sqlBuilder.append("AND t.sid = ? ");
            }
            
            if (name != null && !name.trim().isEmpty()) {
                sqlBuilder.append("AND (s.firstname LIKE ? OR s.lastname LIKE ? OR CONCAT(s.firstname, ' ', s.lastname) LIKE ?) ");
            }
            
            sqlBuilder.append("ORDER BY t.payment_date DESC, t.transaction_id DESC");
            
            PreparedStatement psTransactions = con.prepareStatement(sqlBuilder.toString());
            
            int paramIndex = 1;
            psTransactions.setDate(paramIndex++, Date.valueOf(fromDate));
            psTransactions.setDate(paramIndex++, Date.valueOf(toDate));
            
            if (sid != null && !sid.trim().isEmpty()) {
                psTransactions.setString(paramIndex++, sid);
            }
            
            if (name != null && !name.trim().isEmpty()) {
                String searchPattern = "%" + name.trim() + "%";
                psTransactions.setString(paramIndex++, searchPattern);
                psTransactions.setString(paramIndex++, searchPattern);
                psTransactions.setString(paramIndex++, searchPattern);
            }
            
            ResultSet rsTransactions = psTransactions.executeQuery();
            
            // Process transactions
            JSONArray transactions = new JSONArray();
            
            while (rsTransactions.next()) {
                JSONObject transaction = new JSONObject();
                transaction.put("id", rsTransactions.getInt("transaction_id"));
                transaction.put("sid", rsTransactions.getString("sid"));
                transaction.put("studentName", rsTransactions.getString("student_name"));
                transaction.put("date", rsTransactions.getDate("payment_date").toString());
                transaction.put("amount", rsTransactions.getInt("amount"));
                transaction.put("method", rsTransactions.getString("payment_method"));
                transaction.put("upiRef", rsTransactions.getString("upi_ref"));
                
                transactions.put(transaction);
            }
            
            json.put("transactions", transactions);
            
            // If no results found, add a status message
            if (transactions.isEmpty()) {
                json.put("status", "success");
                json.put("message", "No transactions found for the specified criteria.");
            } else {
                json.put("status", "success");
            }
            
            con.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            json.put("status", "error");
            json.put("message", "Server Error: " + e.getMessage());
        }
        
        out.print(json.toString());
        out.flush();
    }
}