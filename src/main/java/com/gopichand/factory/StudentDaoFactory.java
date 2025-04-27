package com.gopichand.factory;

import com.gopichand.dao.StudentDao;
import com.gopichand.dao.StudentDaoImpl;

public class StudentDaoFactory {
	 private static StudentDao studentDao = null;
	   static {
	       studentDao = new StudentDaoImpl();
	   }
	   public static StudentDao getStudentDao() {
	       return studentDao;
	   }

}
