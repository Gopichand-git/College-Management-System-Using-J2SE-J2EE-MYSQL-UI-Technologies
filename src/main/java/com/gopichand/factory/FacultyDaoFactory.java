package com.gopichand.factory;


import com.gopichand.dao.FacultyDao;
import com.gopichand.dao.FacultyDaoImpl;

public class FacultyDaoFactory {
	 private static FacultyDao facultyDao = null;
	   static {
		   facultyDao = new FacultyDaoImpl();
	   }
	   public static FacultyDao getFacultyDao() {
	       return facultyDao;
	   }
}
