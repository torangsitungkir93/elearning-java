package com.lawencon.elearning.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.ClassEnrollment;

public interface ClassEnrollmentService {
	List<ClassEnrollment> getAll() throws SQLException;
	List<ClassEnrollment> getAllByStudent(Long studentId) throws SQLException;
	ClassEnrollment createClassEnroll(Long studentId,Long clazzId,Long createdById) throws SQLException;
}
