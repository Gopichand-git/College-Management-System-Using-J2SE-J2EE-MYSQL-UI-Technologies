package com.gopichand.marks;
import java.io.IOException;
import java.sql.Connection;

import java.sql.PreparedStatement;

import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet("/AddMarksServlet")
public class AddMarkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
   

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("sid");
        String name = request.getParameter("name");
        String branch = request.getParameter("branch");
        String section = request.getParameter("section");
        int semester = Integer.parseInt(request.getParameter("semester"));

        String[] subjects = request.getParameterValues("subject[]");
        String[] marks = request.getParameterValues("marks[]");
        String[] credits = request.getParameterValues("credits[]");

        
  

        try {
             
         	Connection conn = ConnectionFactory.getConnection();
            String sql = "INSERT INTO marks (sid, name, branch, section, semester, subject, marks, credits) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (int i = 0; i < subjects.length; i++) {
                stmt.setString(1, sid);
                stmt.setString(2, name);
                stmt.setString(3, branch);
                stmt.setString(4, section);
                stmt.setInt(5, semester);
                stmt.setString(6, subjects[i]);
                stmt.setInt(7, Integer.parseInt(marks[i]));
                stmt.setInt(8, Integer.parseInt(credits[i]));
                stmt.addBatch();
            }

            int[] result = stmt.executeBatch();
            if (result.length > 0) {
                response.getWriter().write("success");
            } else {
                response.getWriter().write("failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("failed");
        } 
    }
    
}
