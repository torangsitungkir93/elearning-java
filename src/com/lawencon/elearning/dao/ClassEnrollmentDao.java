package com.lawencon.elearning.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.ClassEnrollment;



public interface ClassEnrollmentDao {
	List<ClassEnrollment> getAll() throws SQLException;
	List<ClassEnrollment> getAllByStudent(Long studentId) throws SQLException;
	ClassEnrollment insert(ClassEnrollment classEnrollment) throws SQLException;
}
