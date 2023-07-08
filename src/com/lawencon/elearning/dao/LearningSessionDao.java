package com.lawencon.elearning.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.LearningSession;

public interface LearningSessionDao {
	List<LearningSession> getAllByClass(Long clazzId) throws SQLException;
	LearningSession insert(LearningSession learningSession) throws SQLException;
}
