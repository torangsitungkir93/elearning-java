package com.lawencon.elearning.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.Clazz;


public interface ClazzDao {
	List<Clazz> getAll() throws SQLException;
	List<Clazz> getByInstructor(Long instructorId) throws SQLException;
	Clazz insert(Clazz clazz) throws SQLException;
}
