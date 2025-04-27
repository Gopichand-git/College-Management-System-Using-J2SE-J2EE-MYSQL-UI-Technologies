package com.gopichand.service;


import com.gopichand.beans.Student;

public interface StudentService {
	   public String addStudent(Student student);
	   public Student searchStudent(String sid);
	   public String updateStudent(Student student);
	   public String deleteStudent(String sid);
	   

}
