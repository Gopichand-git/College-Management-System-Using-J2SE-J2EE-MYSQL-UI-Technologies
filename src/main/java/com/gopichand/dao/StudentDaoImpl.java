package com.gopichand.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import com.gopichand.beans.Student;
import com.gopichand.factory.ConnectionFactory;

public class StudentDaoImpl implements StudentDao{
	   Connection connection = ConnectionFactory.getConnection();
	   @Override
	   public String add(Student student) {
	       String status = "";
	       try{
	           PreparedStatement preparedStatement = connection.prepareStatement("insert into student values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	           preparedStatement.setString(1, student.getSid());
	           preparedStatement.setString(2, student.getSfname());
	           preparedStatement.setString(3, student.getSlname());
	           preparedStatement.setString(4, student.getSaddr());
	           preparedStatement.setString(5, student.getSgender());
	           preparedStatement.setString(6, student.getSemail());
	           preparedStatement.setString(7, student.getSmobile());
	           preparedStatement.setString(8, student.getSfather());
	           preparedStatement.setString(9, student.getSfatherMobile());
	           preparedStatement.setString(10,student.getSmother());
	           preparedStatement.setString(11,student.getSmotherMobile());
	           preparedStatement.setString(12, student.getScourse());
	           preparedStatement.setString(13, student.getSbranch());
	           preparedStatement.setString(14,student.getSsection());
	           preparedStatement.setString(15, student.getSfee());
	           preparedStatement.setString(16, student.getScity());
	           preparedStatement.setString(17, student.getSpincode());
	           preparedStatement.setString(18, student.getSstate());
	           preparedStatement.setString(19,  student.getSjoin());
	           int rowCount = preparedStatement.executeUpdate();
	           if(rowCount == 1){
	               status = "success";
	           }else{
	               status = "failure";
	           }
	       }catch (Exception e){
	           e.printStackTrace();
	       }
	       return status;
	   }


	   @Override
	   public Student search(String sid) {
	       Student student = null;
	       try{
	           Statement stmt = connection.createStatement();
	           ResultSet rs = stmt.executeQuery("select * from student where sid = '" + sid + "'");
	           boolean b = rs.next();
	           if(b == true){
	               student = new Student();
	               student.setSid(rs.getString("SID"));
	               student.setSfname(rs.getString("FIRSTNAME"));
	               student.setSlname(rs.getString("LASTNAME"));
	               student.setSaddr(rs.getString("ADDRESS"));
	               student.setSgender(rs.getString("GENDER"));
	               student.setSemail(rs.getString("EMAIL"));
	               student.setSmobile(rs.getString("MOBILE"));
	               student.setSfather(rs.getString("FATHERNAME"));
	               student.setSfatherMobile(rs.getString("FATHERMOBILE"));
	               student.setSmother(rs.getString("MOTHERNAME"));
	               student.setSmotherMobile(rs.getString("MOTHERMOBILE"));
	               student.setScourse(rs.getString("COURSE"));
	               student.setSbranch(rs.getString("BRANCH"));
	               student.setSsection(rs.getString("SECTION"));
	               student.setSfee(rs.getString("FEE"));
	               student.setScity(rs.getString("CITY"));
	               student.setSpincode(rs.getString("PINCODE"));
	               student.setSstate(rs.getString("STATE"));
	               student.setSjoin(rs.getString("Joining"));
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return student;
	   }


	   @Override
	   public String update(Student student) {
	       String status = "";
	       try{
	           PreparedStatement preparedStatement = connection.prepareStatement(
	                   "update student set  firstname = ?, lastname = ?,address = ?,gender = ?,email = ?,mobile = ?, fathername = ?, fathermobile = ?, mothername = ?, mothermobile = ?, course = ?,branch = ?, section = ? ,fee = ?,  city = ?,pincode = ?,state = ?,joining = ?  where sid = ?"
	           );
	         
	           preparedStatement.setString(1, student.getSfname());
	           preparedStatement.setString(2, student.getSlname());
	           preparedStatement.setString(3, student.getSaddr());
	           preparedStatement.setString(4, student.getSgender());
	           preparedStatement.setString(5, student.getSemail());
	           preparedStatement.setString(6, student.getSmobile());
	           preparedStatement.setString(7, student.getSfather());
	           preparedStatement.setString(8, student.getSfatherMobile());
	           preparedStatement.setString(9,student.getSmother());
	           preparedStatement.setString(10,student.getSmotherMobile());
	           preparedStatement.setString(11, student.getScourse());
	           preparedStatement.setString(12, student.getSbranch());
	           preparedStatement.setString(13,student.getSsection());
	           preparedStatement.setString(14,student.getSfee());
	           preparedStatement.setString(15, student.getScity());
	           preparedStatement.setString(16, student.getSpincode());
	           preparedStatement.setString(17, student.getSstate());
	           preparedStatement.setString(18,  student.getSjoin());
	           preparedStatement.setString(19, student.getSid());
	           
	           int rowCount = preparedStatement.executeUpdate();
	           if(rowCount == 1){
	               status = "success";
	           }else{
	               status = "failure";
	           }
	       } catch (Exception e) {
	           e.printStackTrace();
	       }
	       return status;
	   }


	   @Override
	   public String delete(String sid) {
	       String status = "";
	       try{
	           PreparedStatement preparedStatement = connection.prepareStatement(
	                   "delete from student where sid = ?"
	           );
	           preparedStatement.setString(1, sid);
	           int rowCount = preparedStatement.executeUpdate();
	           if(rowCount == 1){
	               status = "success";
	           }else{
	               status = "failure";
	           }
	       }catch (Exception e){
	           e.printStackTrace();
	       }
	       return status;
	   }
}

	   
	   

	   
	   
	  