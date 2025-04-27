package com.gopichand.Attendance;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.gopichand.factory.ConnectionFactory;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/GetStudentsForAttendanceServlet")
public class GetStudentsForAttendanceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String branch = request.getParameter("branch");
        String section = request.getParameter("section");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if (branch == null || section == null || branch.isEmpty() || section.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Branch and section are required.\"}");
            return;
        }

        List<Map<String, String>> studentList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionFactory.getConnection();
            String query = "SELECT sid, firstname, lastname FROM student WHERE branch = ? AND section = ?";
            stmt = conn.prepareStatement(query);
            
            stmt.setString(1, branch);
            stmt.setString(2, section);
            
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Map<String, String> student = new HashMap<>();
                student.put("sid", rs.getString("sid"));
                
                // Handle potentially null firstname/lastname
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                
                firstname = (firstname != null) ? firstname : "";
                lastname = (lastname != null) ? lastname : "";
                
                student.put("name", firstname + " " + lastname);
                studentList.add(student);
            }
            
            String jsonResponse = new Gson().toJson(studentList);
            response.getWriter().write(jsonResponse);

        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"status\":\"error\",\"message\":\"Error while fetching students: " + e.getMessage() + "\"}");
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}