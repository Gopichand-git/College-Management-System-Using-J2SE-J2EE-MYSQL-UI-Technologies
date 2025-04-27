package com.gopichand.Attendance;

import java.io.*;
import java.sql.*;
import org.json.JSONObject;

import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AttendanceCountServlet")
public class AttendanceCountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

   
        String branch = request.getParameter("branch");
        String section = request.getParameter("section");
        int semester = Integer.parseInt(request.getParameter("semester"));
        String subject = request.getParameter("subject");
        String date = request.getParameter("date");
        

        String query = "SELECT " +
                "SUM(CASE WHEN a.status = 'Present' THEN 1 ELSE 0 END) AS present_count, " +
                "SUM(CASE WHEN a.status = 'Absent' THEN 1 ELSE 0 END) AS absent_count " +
                "FROM attendance a " +
                "JOIN student s ON a.sid = s.sid " +
                "WHERE a.branch = ? AND a.section = ? AND a.semester = ? AND a.subject = ? " +
                "AND a.date BETWEEN ? AND ?";

    
       

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

     
            stmt.setString(1, branch);
            stmt.setString(2, section);
            stmt.setInt(3, semester);
            stmt.setString(4, subject);
            stmt.setString(5, date);
            stmt.setString(6, date);

          
           

            ResultSet rs = stmt.executeQuery();

            JSONObject jsonResponse = new JSONObject();
            if (rs.next()) {
                jsonResponse.put("present_count", rs.getInt("present_count"));
                jsonResponse.put("absent_count", rs.getInt("absent_count"));
            }

            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error fetching attendance data. Please try again.\"}");
        }
    }
}
