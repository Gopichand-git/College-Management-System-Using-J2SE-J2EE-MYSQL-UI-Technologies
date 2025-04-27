package com.gopichand.marks;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gopichand.factory.ConnectionFactory;

@WebServlet("/GetStudentMarksServlet")
public class GetMarkServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unused")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sid = request.getParameter("sid");

        try {
            Connection con = ConnectionFactory.getConnection();
            String sql = "SELECT id, name, subject, marks, credits, semester FROM marks WHERE sid = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, sid);
            ResultSet rs = ps.executeQuery();

            StringBuilder html = new StringBuilder();
            String semester = null;

            html.append("<form id='updateMarksForm' method='post' action='UpdateStudentMarksServlet'>");
            html.append("<input type='hidden' name='sid' value='").append(sid).append("' />");

            html.append("<div class='table-responsive'><table class='table table-bordered'><thead class='table-primary'>");
            html.append("<tr><th>Name</th><th>Subject</th><th>Marks</th><th>Credits</th></tr></thead><tbody>");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String subject = rs.getString("subject");
                int marks = rs.getInt("marks");
                int credits = rs.getInt("credits");
                semester = rs.getString("semester"); 
   
                name = name.replace("<", "&lt;").replace(">", "&gt;");
                subject = subject.replace("<", "&lt;").replace(">", "&gt;");

                html.append("<tr>");
                html.append("<td><input type='text' class='form-control' name='name_").append(subject)
                        .append("' value='").append(name).append("' required></td>");
                html.append("<td><input type='text' class='form-control' name='subject_").append(subject)
                        .append("' value='").append(subject).append("' required></td>");
                html.append("<td><input type='number' class='form-control' name='marks_").append(subject)
                        .append("' value='").append(marks).append("' min='0' max='100' required></td>");
                html.append("<td><input type='number' class='form-control' name='credits_").append(subject)
                        .append("' value='").append(credits).append("' min='1' max='10' required></td>");
                html.append("</tr>");
            }

            html.append("</tbody></table></div>");

            if (semester != null) {
                html.append("<input type='hidden' name='semester' value='").append(semester).append("' />");
            }

            html.append("<button type='submit' class='btn btn-success w-100 mt-3'>Update Marks</button>");
            html.append("</form>");

            response.setContentType("text/html");
            response.getWriter().write(html.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("<div class='alert alert-danger'>Error loading student data</div>");
        }
    }
  
}
