package com.gopichand.Validations;

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

@WebServlet("/CheckEmailServlet")
public class CheckEmailServlet extends HttpServlet {
	
	public void init(){
	       ConnectionFactory.getConnection();
	 }
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		  String email = request.getParameter("email");
	      PrintWriter out = response.getWriter();
	      
	      try
	      {
	    	  		Connection con = ConnectionFactory.getConnection();
	    	  		PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email = ?");
	                ps.setString(1, email);
	                ResultSet rs = ps.executeQuery();

	                if (rs.next()) {
	                    out.print("exists");
	                } else {
	                    out.print("available");
	                }
	    	  		
	      }
	      catch (Exception e) {
			e.printStackTrace();
			  response.setStatus(500);
		}
	      
	      
	}
	

}
