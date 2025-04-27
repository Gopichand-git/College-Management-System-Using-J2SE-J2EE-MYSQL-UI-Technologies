package com.gopichand.factory;

import com.gopichand.service.FacultyService;
import com.gopichand.service.FacultyServiceImpl;


public class FacultyServiceFactory {
	private static FacultyService facultyService = null;
	   static {
		   facultyService = new FacultyServiceImpl();
	   }
	   public static FacultyService getFacultyService() {
	       return facultyService;
	   }

}
