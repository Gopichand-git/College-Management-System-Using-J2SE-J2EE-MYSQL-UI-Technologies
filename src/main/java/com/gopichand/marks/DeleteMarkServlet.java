package com.gopichand.marks;
import java.io.*;



import com.gopichand.factory.ConnectionFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.sql.*;

@WebServlet("/DeleteMarksServlet")
public class DeleteMarkServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	  public void init() {
	        ConnectionFactory.getConnection();
	    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
        response.setContentType("text/html");


        String sid = request.getParameter("sid");

    
        PrintWriter out = response.getWriter();
      
        String sql = "DELETE FROM marks WHERE sid = ?";

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            
     
            statement.setString(1, sid);


            int rowsAffected = statement.executeUpdate();

     
            if (rowsAffected > 0) {
                out.println("Marks deleted successfully for SID: " + sid);
            } else {
                out.println("No record found for SID: " + sid);
            }
        } catch (SQLException e) {
    
            e.printStackTrace();
            out.println("An error occurred while processing the request.");
        }
    }
	

}
