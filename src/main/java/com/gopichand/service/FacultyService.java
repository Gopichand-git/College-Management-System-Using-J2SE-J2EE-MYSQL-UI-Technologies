package com.gopichand.service;

import com.gopichand.beans.Faculty;


public interface FacultyService {
	   public String addFaculty(Faculty faculty);
	   public Faculty searchFaculty(String fid);
	   public String updateFaculty(Faculty faculty);
	   public String deleteFaculty(String fid);
}
