package com.gopichand.factory;

import com.gopichand.service.StudentService;
import com.gopichand.service.StudentServiceImpl;

public class StudentServiceFactory {
	private static StudentService studentService = null;
	   static {
	       studentService = new StudentServiceImpl();
	   }
	   public static StudentService getStudentService() {
	       return studentService;
	   }

}
