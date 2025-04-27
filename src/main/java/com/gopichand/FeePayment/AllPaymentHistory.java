package com.gopichand.FeePayment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AllStudentsPaymentHistoryServlet")
public class AllPaymentHistory extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            // Get filter parameters (optional)
            String course = request.getParameter("course");
            String branch = request.getParameter("branch");
            String section = request.getParameter("section");
            String fromDate = request.getParameter("fromDate");
            String toDate = request.getParameter("toDate");
            String paymentMethod = request.getParameter("paymentMethod");
            
            // Log received parameters for debugging
            System.out.println("Received filters - Course: " + course + 
                               ", Branch: " + branch + 
                               ", Section: " + section + 
                               ", From Date: " + fromDate + 
                               ", To Date: " + toDate + 
                               ", Payment Method: " + paymentMethod);
            
            // Database connection
            con = ConnectionFactory.getConnection();
            if (con == null) {
                throw new SQLException("Failed to obtain database connection");
            }
            System.out.println("Database connection established successfully");
            
            // Build query to get all payment records with student information
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT s.sid, s.firstname, s.lastname, s.course, s.branch, s.section, s.fee, ");
            queryBuilder.append("p.paid_fee, p.payment_method, p.upi_ref, p.payment_date ");
            queryBuilder.append("FROM student s ");
            queryBuilder.append("JOIN payments p ON s.sid = p.sid ");
            queryBuilder.append("WHERE 1=1 "); // Ensure all filters are appended conditionally

            // Add filters if provided - using case-insensitive comparison for text fields
            if (course != null && !course.isEmpty()) {
                queryBuilder.append("AND UPPER(s.course) = UPPER(?) ");  // UPPER() for case-insensitivity
            }
            if (branch != null && !branch.isEmpty()) {
                queryBuilder.append("AND UPPER(s.branch) = UPPER(?) ");
            }
            if (section != null && !section.isEmpty()) {
                queryBuilder.append("AND UPPER(s.section) = UPPER(?) ");
            }

            // Add date filters if provided
            if (fromDate != null && !fromDate.isEmpty()) {
                queryBuilder.append("AND p.payment_date >= ? "); // Date range starting from 'fromDate'
            }
            if (toDate != null && !toDate.isEmpty()) {
                queryBuilder.append("AND p.payment_date <= ? "); // Date range ending at 'toDate'
            }

            // Filter by payment method if provided
            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                queryBuilder.append("AND UPPER(p.payment_method) = UPPER(?) "); // UPPER() for case-insensitivity
            }

            // Sorting the results by payment date in descending order
            queryBuilder.append("ORDER BY p.payment_date DESC");

            // Convert the StringBuilder to a final query string
            String finalQuery = queryBuilder.toString();
            System.out.println("Generated SQL: " + finalQuery);

            // Now you can use the final query in a PreparedStatement
            ps = con.prepareStatement(finalQuery);

            // Set parameters for filters
            int paramIndex = 1;
            if (course != null && !course.isEmpty()) {
                ps.setString(paramIndex++, course);
                System.out.println("Param " + (paramIndex-1) + ": course = " + course);
            }
            if (branch != null && !branch.isEmpty()) {
                ps.setString(paramIndex++, branch);
                System.out.println("Param " + (paramIndex-1) + ": branch = " + branch);
            }
            if (section != null && !section.isEmpty()) {
                ps.setString(paramIndex++, section);
                System.out.println("Param " + (paramIndex-1) + ": section = " + section);
            }
            if (fromDate != null && !fromDate.isEmpty()) {
                String formattedFromDate = fromDate + " 00:00:00";
                ps.setString(paramIndex++, formattedFromDate);
                System.out.println("Param " + (paramIndex-1) + ": fromDate = " + formattedFromDate);
            }
            if (toDate != null && !toDate.isEmpty()) {
                String formattedToDate = toDate + " 23:59:59";
                ps.setString(paramIndex++, formattedToDate);
                System.out.println("Param " + (paramIndex-1) + ": toDate = " + formattedToDate);
            }
            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                ps.setString(paramIndex++, paymentMethod);
                System.out.println("Param " + (paramIndex-1) + ": paymentMethod = " + paymentMethod);
            }
            // Execute query
            System.out.println("Executing query...");
            rs = ps.executeQuery();
            
            // Store student payment history
            Map<String, JSONObject> studentsMap = new HashMap<>();
            JSONArray paymentsArray = new JSONArray();
            int recordCount = 0;
            
            while (rs.next()) {
                recordCount++;
                String studentId = rs.getString("sid");
                
                // Create a JSON object for each payment
                JSONObject payment = new JSONObject();
                payment.put("sid", studentId);
                payment.put("firstname", rs.getString("firstname"));
                payment.put("lastname", rs.getString("lastname"));
                payment.put("course", rs.getString("course"));
                payment.put("branch", rs.getString("branch"));
                payment.put("section", rs.getString("section"));
                payment.put("totalFee", rs.getDouble("fee"));
                payment.put("paidAmount", rs.getDouble("paid_fee"));
                payment.put("paymentMethod", rs.getString("payment_method"));
                payment.put("upiReference", rs.getString("upi_ref"));
                payment.put("paymentDate", rs.getString("payment_date"));
                
                paymentsArray.put(payment);
                
                // Track unique students and their total payments
                if (!studentsMap.containsKey(studentId)) {
                    JSONObject studentSummary = new JSONObject();
                    studentSummary.put("sid", studentId);
                    studentSummary.put("name", rs.getString("firstname") + " " + rs.getString("lastname"));
                    studentSummary.put("course", rs.getString("course"));
                    studentSummary.put("branch", rs.getString("branch"));
                    studentSummary.put("section", rs.getString("section"));
                    studentSummary.put("totalFee", rs.getDouble("fee"));
                    studentSummary.put("totalPaid", rs.getDouble("paid_fee"));
                    studentSummary.put("paymentCount", 1);
                    studentsMap.put(studentId, studentSummary);
                } else {
                    JSONObject existingStudent = studentsMap.get(studentId);
                    double currentPaid = existingStudent.getDouble("totalPaid");
                    int currentCount = existingStudent.getInt("paymentCount");
                    existingStudent.put("totalPaid", currentPaid + rs.getDouble("paid_fee"));
                    existingStudent.put("paymentCount", currentCount + 1);
                }
            }
            
            System.out.println("Query executed, found " + recordCount + " records");
            
            // Create the final result structure
            JSONArray studentsArray = new JSONArray();
            for (JSONObject student : studentsMap.values()) {
                double totalFee = student.getDouble("totalFee");
                double totalPaid = student.getDouble("totalPaid");
                student.put("remainingFee", totalFee - totalPaid);
                studentsArray.put(student);
            }
            
            result.put("success", true);
            result.put("studentSummaries", studentsArray);
            result.put("payments", paymentsArray);
            result.put("recordCount", recordCount);
            
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error in AllPaymentHistory servlet: " + e.getMessage());
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("errorType", e.getClass().getName());
            // Add stack trace to response for debugging
            StringBuilder stackTrace = new StringBuilder();
            for (StackTraceElement element : e.getStackTrace()) {
                stackTrace.append(element.toString()).append("\n");
            }
            result.put("stackTrace", stackTrace.toString());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
                System.out.println("Resources closed successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
        
        // Send the response
        String jsonResponse = result.toString();
        System.out.println("Sending response: " + (jsonResponse.length() > 200 ? 
                           jsonResponse.substring(0, 200) + "..." : jsonResponse));
        out.print(jsonResponse);
        out.flush();
    }
}