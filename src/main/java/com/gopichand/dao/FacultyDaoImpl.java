package com.gopichand.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.gopichand.beans.Faculty;
import com.gopichand.factory.ConnectionFactory;


public class FacultyDaoImpl implements FacultyDao {

	Connection connection = ConnectionFactory.getConnection();
	@Override
	public String add(Faculty faculty) {
		String status = "";
		try
		{
			PreparedStatement preparedStatement = connection.prepareStatement("insert into faculty values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			   preparedStatement.setString(1, faculty.getFid());
			   preparedStatement.setString(2, faculty.getFirstname());
	           preparedStatement.setString(3, faculty.getLastname());
	           preparedStatement.setString(4, faculty.getGender());
	           preparedStatement.setString(5, faculty.getAddress());
	           preparedStatement.setString(6, faculty.getEmail());
	           preparedStatement.setString(7, faculty.getMobile());
	           
	           preparedStatement.setString(8, faculty.getCourse());
	           preparedStatement.setString(9, faculty.getBranch());
	           preparedStatement.setString(10, faculty.getDesignation());
	           preparedStatement.setString(11, faculty.getQualification());
	          
	           preparedStatement.setString(12, faculty.getSalary());
	           preparedStatement.setString(13, faculty.getCity());
	           preparedStatement.setString(14, faculty.getPincode());
	           preparedStatement.setString(15, faculty.getState());
	           preparedStatement.setString(16, faculty.getJoin());
	           
	           int rowCount = preparedStatement.executeUpdate();
	           if(rowCount == 1){
	               status = "success";
	           }else{
	               status = "failure";
	           }
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return status;
	}

	@Override
	public Faculty search(String fid) {
		Faculty faculty = null;
		try{
	           Statement stmt = connection.createStatement();
	           ResultSet rs = stmt.executeQuery("select * from faculty where fid = '" + fid + "'");
	           boolean b = rs.next();
	           if(b == true){
	        	   faculty = new Faculty();
	               faculty.setFid(rs.getString("FID"));
	               faculty.setFirstname(rs.getString("FIRSTNAME"));
	               faculty.setLastname(rs.getString("LASTNAME"));
	               faculty.setGender(rs.getString("GENDER"));
	               faculty.setAddress(rs.getString("ADDRESS"));
	               faculty.setEmail(rs.getString("EMAIL"));
	               faculty.setMobile(rs.getString("MOBILE"));
	               
	               faculty.setCourse(rs.getString("COURSE"));
	               faculty.setBranch(rs.getString("BRANCH"));
	               faculty.setDesignation(rs.getString("DESIGNATION"));
	               faculty.setQualification(rs.getString("QUALIFICATION"));
	              
	               
	               faculty.setSalary(rs.getString("SALARY"));
	               faculty.setCity(rs.getString("CITY"));
	               faculty.setPincode(rs.getString("PINCODE"));
	               faculty.setState(rs.getString("STATE"));
	               faculty.setJoin(rs.getString("Joining_Date"));
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
		return faculty;
	}

	@Override
	public String update(Faculty faculty) {
	    String status = "";
	    try {
	        PreparedStatement preparedStatement = connection.prepareStatement(
	            "UPDATE faculty SET FIRSTNAME = ?, LASTNAME = ?, GENDER = ?, ADDRESS = ?, EMAIL = ?, MOBILE = ?, " +
	            "COURSE = ?, BRANCH = ?, DESIGNATION = ?, QUALIFICATION = ?, SALARY = ?, CITY = ?, PINCODE = ?, " +
	            "STATE = ?, JOINING_DATE = ? WHERE FID = ?"
	        );
	        
	        preparedStatement.setString(1, faculty.getFirstname());
	        preparedStatement.setString(2, faculty.getLastname());
	        preparedStatement.setString(3, faculty.getGender()); // Make sure this field size matches your DB column
	        preparedStatement.setString(4, faculty.getAddress());
	        preparedStatement.setString(5, faculty.getEmail());
	        preparedStatement.setString(6, faculty.getMobile());
	        preparedStatement.setString(7, faculty.getCourse());
	        preparedStatement.setString(8, faculty.getBranch());
	        preparedStatement.setString(9, faculty.getDesignation());
	        preparedStatement.setString(10, faculty.getQualification());
	        preparedStatement.setString(11, faculty.getSalary());
	        preparedStatement.setString(12, faculty.getCity());
	        preparedStatement.setString(13, faculty.getPincode());
	        preparedStatement.setString(14, faculty.getState());
	        preparedStatement.setString(15, faculty.getJoin());
	        preparedStatement.setString(16, faculty.getFid());

	        int rowCount = preparedStatement.executeUpdate();
	        if (rowCount == 1) {
	            status = "success";
	        } else {
	            status = "failure";
	        }
	    } catch (SQLException e) {
	        // Handle SQL exceptions specifically
	        if (e.getMessage().contains("Data truncation")) {
	            System.err.println("Data truncation error: " + e.getMessage());
	            // Provide more specific feedback
	            status = "data_too_long";
	        } else {
	            e.printStackTrace();
	            status = "failure";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        status = "failure";
	    }

	    return status;
	}

	@Override
	// This is the updated DAO method for faculty deletion

	public String delete(String fid) {
	    String status = "";
	    try {
	        // Debug statement
	        System.out.println("DAO: Deleting faculty with ID: " + fid);
	        
	        PreparedStatement preparedStatement = connection.prepareStatement(
	            "DELETE FROM faculty WHERE fid = ?"
	        );
	        preparedStatement.setString(1, fid);
	        
	        int rowCount = preparedStatement.executeUpdate();
	        
	        // Debug statement
	        System.out.println("DAO: Rows affected: " + rowCount);
	        
	        if (rowCount == 1) {
	            status = "success";
	        } else {
	            status = "failure";
	        }
	    } catch (Exception e) {
	        System.out.println("DAO: Exception during deletion: " + e.getMessage());
	        e.printStackTrace();
	        status = "failure";
	    }
	    return status;
	}

}
