package com.gopichand.dao;

import com.gopichand.beans.Faculty;


public interface FacultyDao {
	
	   public String add(Faculty faculty);
	   public Faculty search(String fid);
	   public String update(Faculty faculty);
	   public String delete(String fid);
}
