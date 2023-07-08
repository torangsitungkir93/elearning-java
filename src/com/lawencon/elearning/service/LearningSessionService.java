package com.lawencon.elearning.service;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.lawencon.elearning.model.LearningSession;


public interface LearningSessionService {
	List<LearningSession> getAllByClass(Long ClazzId) throws SQLException;
	LearningSession createLearningSession(Timestamp startTime,Timestamp endTime, Long clazzId,Long createdById) throws SQLException;
}
