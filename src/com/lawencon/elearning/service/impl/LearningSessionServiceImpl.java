package com.lawencon.elearning.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.elearning.config.DbConfig;
import com.lawencon.elearning.dao.LearningSessionDao;
import com.lawencon.elearning.dao.impl.LearningSessionDaoImpl;
import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.model.LearningSession;
import com.lawencon.elearning.service.LearningSessionService;

public class LearningSessionServiceImpl implements LearningSessionService{
	private final LearningSessionDao learningSessionDao;
	private final Connection conn = DbConfig.conn();
	
	public LearningSessionServiceImpl() throws SQLException{	
		learningSessionDao = new LearningSessionDaoImpl(conn);
	}
	
	@Override
	public List<LearningSession> getAllByClass(Long ClazzId) throws SQLException {
		final List<LearningSession> learningSessionList = learningSessionDao.getAllByClass(ClazzId);
		return learningSessionList;
	}

	@Override
	public LearningSession createLearningSession(Timestamp startTime, Timestamp endTime, Long clazzId, Long createdById)
			throws SQLException {
		final LearningSession learningSession= new LearningSession();
		final Clazz clazz = new Clazz();
		
		try {
			
			learningSession.setStartTime(startTime.toLocalDateTime());
			learningSession.setEndTime(endTime.toLocalDateTime());
			
			clazz.setId(clazzId);
			learningSession.setClazz(clazz);
			learningSession.setCreatedBy(createdById);
			final LocalDateTime timeNow = LocalDateTime.now();
			learningSession.setCreatedAt(timeNow);
			learningSession.setIsActive(true);
			learningSession.setVer(0);
			
			learningSessionDao.insert(learningSession);
			conn.commit();
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return learningSession;
	}

}
