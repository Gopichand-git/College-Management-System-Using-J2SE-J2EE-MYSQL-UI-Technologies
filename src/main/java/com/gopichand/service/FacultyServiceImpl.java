package com.gopichand.service;

import com.gopichand.beans.Faculty;
import com.gopichand.dao.FacultyDao;
import com.gopichand.factory.FacultyDaoFactory;


public class FacultyServiceImpl implements FacultyService{

	FacultyDao facultyDao = FacultyDaoFactory.getFacultyDao();
	@Override
	public String addFaculty(Faculty faculty) {
	       String status = facultyDao.add(faculty);
	       return status;
	   }


	@Override
	public Faculty searchFaculty(String fid) {
		Faculty faculty = facultyDao.search(fid);
		return faculty;
	}

	@Override
	public String updateFaculty(Faculty faculty) {
		String status = facultyDao.update(faculty);
		return status;
	}

	@Override
	public String deleteFaculty(String fid) {
		String status = facultyDao.delete(fid);
		return status;
	}

}
