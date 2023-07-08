package com.lawencon.elearning.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.model.File;


public interface ClazzService {
	List<Clazz> getAll() throws SQLException;
	List<Clazz> getByInstructor(Long instructorId) throws SQLException;
	Clazz createClazz(String classLearning,String classDescription, Long userId,String fileName,Long createdById) throws SQLException;
}
