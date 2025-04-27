package com.gopichand.service;


import com.gopichand.beans.Student;
import com.gopichand.dao.StudentDao;
import com.gopichand.factory.StudentDaoFactory;

public class StudentServiceImpl implements StudentService{

	StudentDao studentDao = StudentDaoFactory.getStudentDao();
	   @Override
	   public String addStudent(Student student) {
	       String status = studentDao.add(student);
	       return status;
	   }


	   @Override
	   public Student searchStudent(String sid) {
	       Student student = studentDao.search(sid);
	       return student;
	   }


	   @Override
	   public String updateStudent(Student student) {
	       String status = studentDao.update(student);
	       return status;
	   }


	   @Override
	   public String deleteStudent(String sid) {
	       String status = studentDao.delete(sid);
	       return status;
	   }
}



