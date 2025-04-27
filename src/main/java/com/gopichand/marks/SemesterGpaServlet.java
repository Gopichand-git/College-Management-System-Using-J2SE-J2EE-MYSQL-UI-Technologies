package com.gopichand.marks;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import com.gopichand.factory.ConnectionFactory;

@WebServlet("/SemesterGpaServlet")
public class SemesterGpaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void init() {
        ConnectionFactory.getConnection();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sid = request.getParameter("sid");
        String semester = request.getParameter("semester");
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        // Validate SID and Semester parameters
        if (sid == null || sid.trim().isEmpty() || semester == null || semester.trim().isEmpty()) {
            out.print("SID and semester are required");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = ConnectionFactory.getConnection();

            // Check if connection is valid
            if (con == null || con.isClosed()) {
                throw new SQLException("Connection is closed or not established.");
            }

            String sql = "SELECT SUM(marks * credits) AS totalPoints, SUM(credits) AS totalCredits " +
                         "FROM marks WHERE sid = ? AND semester = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, sid);
            ps.setString(2, semester);

            rs = ps.executeQuery();
            if (rs.next()) {
                double totalPoints = rs.getDouble("totalPoints");
                double totalCredits = rs.getDouble("totalCredits");

                // Calculate GPA if totalCredits > 0
                if (totalCredits > 0) {
                    double gpa = totalPoints / totalCredits;
                    out.printf("%.2f", gpa);  // Format GPA to 2 decimal places
                } else {
                    out.print("0.00");  // Return GPA as 0.00 if no credits
                }
            } else {
                out.print("0.00");  // Return GPA as 0.00 if no data found
            }

        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
            out.print("Error calculating GPA");
        } 
    }
   
}
