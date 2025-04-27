package com.gopichand.marks;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.gopichand.factory.ConnectionFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Enumeration;

@WebServlet("/UpdateStudentMarksServlet")
public class UpdateMarkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String sid = request.getParameter("sid");
        String semester = request.getParameter("semester");

        if (sid == null || semester == null) {
            response.setContentType("text/plain");
            response.getWriter().write("SID or Semester is missing!");
            return;
        }

        int updateCount = 0;

        try (Connection con = ConnectionFactory.getConnection()) {
            Enumeration<String> paramNames = request.getParameterNames();

            while (paramNames.hasMoreElements()) {
                String paramName = paramNames.nextElement();

                if (paramName.startsWith("subject_")) {
                    String subjectKey = paramName.substring("subject_".length()); // e.g. "MATHS"

                    String subject = request.getParameter("subject_" + subjectKey);
                    String name = request.getParameter("name_" + subjectKey);
                    String marksStr = request.getParameter("marks_" + subjectKey);
                    String creditsStr = request.getParameter("credits_" + subjectKey);

                    if (subject == null || name == null || marksStr == null || creditsStr == null) {
                        continue; // skip if any field is missing
                    }

                    try {
                        int marks = Integer.parseInt(marksStr);
                        int credits = Integer.parseInt(creditsStr);

                        String sql = "UPDATE marks SET name = ?, marks = ?, credits = ? WHERE sid = ? AND subject = ? AND semester = ?";
                        PreparedStatement ps = con.prepareStatement(sql);
                        ps.setString(1, name);
                        ps.setInt(2, marks);
                        ps.setInt(3, credits);
                        ps.setString(4, sid);
                        ps.setString(5, subject);
                        ps.setString(6, semester);

                        int rowsAffected = ps.executeUpdate();
                        
                        if (rowsAffected > 0) {
                            updateCount++;
                        }

                    } catch (NumberFormatException e) {
                        // Log or handle invalid input
                        e.printStackTrace();
                    }
                }
            }

            if (updateCount > 0) {
                response.setContentType("text/plain");
                response.getWriter().write("Marks updated successfully for " + updateCount + " subject(s).");
            } else {
                response.setContentType("text/plain");
                response.getWriter().write("Failed to update any marks. Please check your input.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.setContentType("text/plain");
            response.getWriter().write("Server error while updating marks.");
        }
    }
    

}
