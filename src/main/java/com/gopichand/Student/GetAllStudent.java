package com.gopichand.Student;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/StudentServlet")
public class GetAllStudent extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        // Get filter parameters
        String course = request.getParameter("course");
        String branch = request.getParameter("branch");
        String section = request.getParameter("section");
        
        // For debugging
        System.out.println("Received parameters - course: " + course + ", branch: " + branch + ", section: " + section);
        
        JSONArray students = new JSONArray();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = ConnectionFactory.getConnection();
            
            // Create base query
            String query = "SELECT * FROM student";
            
            // Add WHERE clause if any filters are provided
            boolean hasFilter = false;
            
            if (course != null && !course.isEmpty()) {
                query += " WHERE course = '" + course + "'";
                hasFilter = true;
            }
            
            if (branch != null && !branch.isEmpty()) {
                if (hasFilter) {
                    query += " AND branch = '" + branch + "'";
                } else {
                    query += " WHERE branch = '" + branch + "'";
                    hasFilter = true;
                }
            }
            
            if (section != null && !section.isEmpty()) {
                if (hasFilter) {
                    query += " AND section = '" + section + "'";
                } else {
                    query += " WHERE section = '" + section + "'";
                }
            }
            
            // For debugging
            System.out.println("Executing query: " + query);
            
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            
            while (rs.next()) {
                JSONObject student = new JSONObject();
                student.put("id", rs.getString("sid"));
                student.put("firstname", rs.getString("firstname"));
                student.put("lastname", rs.getString("lastname"));
                student.put("course", rs.getString("course"));
                student.put("branch", rs.getString("branch"));
                student.put("section", rs.getString("section"));
                students.put(student);
            }
            
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }
        
        // For debugging
        System.out.println("Sending response with " + students.length() + " students");
        
        out.print(students.toString());
    }
}