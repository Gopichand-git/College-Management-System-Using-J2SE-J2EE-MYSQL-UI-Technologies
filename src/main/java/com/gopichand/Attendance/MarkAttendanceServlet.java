package com.gopichand.Attendance;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import com.gopichand.factory.ConnectionFactory;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MarkAttendanceServlet")
public class MarkAttendanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] students = request.getParameterValues("students[]");
        String date = request.getParameter("date");
        String branch = request.getParameter("branch");
        String section = request.getParameter("section");
        String subject = request.getParameter("subject");
        String semester = request.getParameter("semester");
        String course = request.getParameter("course");
//        String name = request.getParameter("name");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (students == null || students.length == 0) {
            String jsonResponse = new Gson().toJson(Map.of("status", "error", "message", "No students selected."));
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(jsonResponse);
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            // Get a fresh connection for this request
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false); // Begin transaction
            
            String query = "INSERT INTO attendance (sid, branch, section, subject, date, status, semester, course) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(query);

            for (String sid : students) {
                stmt.setString(1, sid);
                stmt.setString(2, branch);
                stmt.setString(3, section);
                stmt.setString(4, subject);
                stmt.setString(5, date);
                stmt.setString(6, "Present");
                stmt.setInt(7, Integer.parseInt(semester));
                stmt.setString(8, course);
//                stmt.setString(9, name);
                stmt.addBatch();
            }

            stmt.executeBatch();
            conn.commit();


            String successMessage = String.format("Attendance marked successfully for students: ");
            String jsonResponse = new Gson().toJson(Map.of("status", "success", "message", successMessage));
            response.getWriter().write(jsonResponse);

        } catch (SQLException e) {
            e.printStackTrace();
            
            // Attempt rollback if needed
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            
            String errorMessage = "Error your Entering Same Date Again ";
            String jsonResponse = new Gson().toJson(Map.of("status", "error", "message", errorMessage));
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(jsonResponse);
            
        } finally {
            // Close resources in proper order
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if (conn != null) {
                try {
                    conn.close(); // Return connection to pool or close it
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}