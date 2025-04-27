package com.gopichand.marks;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.gopichand.factory.ConnectionFactory;

/**
 * Servlet implementation class ViewMarkServlet
 */
@WebServlet("/ViewMarkServlet")
public class ViewMarkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 public void init() {
	        ConnectionFactory.getConnection();
	    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();

	        String sid = request.getParameter("sid");
	        String branch = request.getParameter("branch");
	        String semester = request.getParameter("semester");
	        String section = request.getParameter("section");

	        try {
	            // Database connection
	           Connection con = ConnectionFactory.getConnection();
	            // Build dynamic SQL query
	            StringBuilder sql = new StringBuilder("SELECT * FROM marks WHERE 1=1");
	            if (sid != null && !sid.isEmpty()) {
	                sql.append(" AND sid = ?");
	            }
	            if (branch != null && !branch.isEmpty()) {
	                sql.append(" AND branch = ?");
	            }
	            if (section != null && !section.isEmpty()) {
	                sql.append(" AND section = ?");
	            }
	            if (semester != null && !semester.isEmpty()) {
	                sql.append(" AND semester = ?");
	            }

	            PreparedStatement ps = con.prepareStatement(sql.toString());

	            // Bind values
	            int index = 1;
	            if (sid != null && !sid.isEmpty()) {
	                ps.setString(index++, sid);
	            }
	            if (branch != null && !branch.isEmpty()) {
	                ps.setString(index++, branch);
	            }
	            if (section != null && !section.isEmpty()) {
	                ps.setString(index++, section);
	            }
	            if (semester != null && !semester.isEmpty()) {
	                ps.setString(index++, semester);
	            }

	            ResultSet rs = ps.executeQuery();

	            boolean hasData = false;
	            out.println("<table class='table table-bordered table-hover'>");
	            out.println("<thead class='table-primary'><tr><th>SID</th><th>Name</th><th>Branch</th><th>Section</th><th>Semester</th><th>Subject</th><th>Marks</th><th>Credits</th></tr></thead><tbody>");
	            while (rs.next()) {
	                hasData = true;
	                out.println("<tr>");
	                out.println("<td>" + rs.getString("sid") + "</td>");
	                out.println("<td>" + rs.getString("name") + "</td>");
	                out.println("<td>" + rs.getString("branch") + "</td>");
	                out.println("<td>" + rs.getString("section") + "</td>");
	                out.println("<td>" + rs.getString("semester") + "</td>");
	                out.println("<td>" + rs.getString("subject") + "</td>");
	                out.println("<td>" + rs.getInt("marks") + "</td>");
	                out.println("<td>" + rs.getInt("credits") + "</td>");
	                out.println("</tr>");
	            }
	            out.println("</tbody></table>");

	            if (!hasData) {
	                out.println("<div class='alert alert-warning text-center'>No marks found for the given criteria.</div>");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            out.println("<div class='alert alert-danger'>Error loading data: " + e.getMessage() + "</div>");
	        }
	    }
	

}
