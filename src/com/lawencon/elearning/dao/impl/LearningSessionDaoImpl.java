package com.lawencon.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lawencon.elearning.dao.LearningSessionDao;
import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.model.LearningSession;

public class LearningSessionDaoImpl implements LearningSessionDao{
	private final Connection conn;
	
	public LearningSessionDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<LearningSession> getAllByClass(Long clazzId) throws SQLException {
		final List<LearningSession> learningSessions = new ArrayList<>();
		final String sql = "SELECT * FROM t_learning_session tls "
				+ "INNER JOIN t_class tc ON tc.id = tls.class_id "
				+ "WHERE tc.id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, clazzId);
		final ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			final LearningSession learningSession = new LearningSession();
			learningSession.setId(rs.getLong("id"));
			learningSession.setStartTime(rs.getTimestamp("start_time").toLocalDateTime());
			learningSession.setEndTime(rs.getTimestamp("end_time").toLocalDateTime());
			
			final Clazz clazz = new Clazz();
			clazz.setId(rs.getLong("class_id"));
			clazz.setClassLearning(rs.getString("class_learning"));
			learningSession.setClazz(clazz);

			learningSession.setCreatedBy(rs.getLong("created_by"));
			learningSession.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getLong("updated_by") != 0) {
				learningSession.setUpdatedBy(rs.getLong("updated_by"));
			}
			if (rs.getTimestamp("updated_at") != null) {
				learningSession.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			learningSession.setIsActive(rs.getBoolean("is_active"));
			learningSession.setVer(rs.getInt("ver"));
			learningSessions.add(learningSession);
		}
		return learningSessions;
	}

	@Override
	public LearningSession insert(LearningSession learningSession) throws SQLException {
		final String sql = "INSERT INTO t_learning_session(start_time, end_time,class_id,created_by, created_at, is_active, ver) VALUES (?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setTimestamp(1, Timestamp.valueOf(learningSession.getStartTime()));
		ps.setTimestamp(2, Timestamp.valueOf(learningSession.getEndTime()));
		ps.setLong(3, learningSession.getClazz().getId());
		ps.setLong(4, learningSession.getCreatedBy());
		ps.setTimestamp(5, Timestamp.valueOf(learningSession.getCreatedAt()));
		ps.setBoolean(6, learningSession.getIsActive());
		ps.setInt(7, learningSession.getVer());	
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {	
			learningSession.setId(rs.getLong("id"));
		}
		conn.commit();
		return learningSession;
	}

}
