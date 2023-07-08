package com.lawencon.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.lawencon.elearning.dao.ForumsDao;
import com.lawencon.elearning.model.Forums;

public class ForumsDaoImpl implements ForumsDao{
private final Connection conn;
	
	public ForumsDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Forums insert(Forums forum) throws SQLException {
		final String sql = "INSERT INTO t_forums(title, body,learning_session_id,created_by, created_at, is_active, ver) VALUES (?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, forum.getTitle());
		ps.setString(2, forum.getBody());
		ps.setLong(3,forum.getLearningSession().getId());
		ps.setLong(4, forum.getCreatedBy());
		ps.setTimestamp(4, Timestamp.valueOf(forum.getCreatedAt()));
		ps.setBoolean(5, forum.getIsActive());
		ps.setInt(6, forum.getVer());	
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {	
			forum.setId(rs.getLong("id"));
		}
		conn.commit();
		return forum;
	}
}
