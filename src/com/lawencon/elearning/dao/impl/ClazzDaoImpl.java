package com.lawencon.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lawencon.elearning.dao.ClazzDao;
import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Profile;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.User;

public class ClazzDaoImpl implements ClazzDao{
	private final Connection conn;
	
	public ClazzDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<Clazz> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_class tc "
				+ "INNER JOIN t_users tu ON tu.id = tc.instructor_id "
				+ "INNER JOIN t_profile tp ON tp.id = tu.profile_id ";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		List<Clazz> clazzez = new ArrayList<>();
		
		while(rs.next()) {	
			final Clazz clazz = new Clazz();
			clazz.setId(rs.getLong("id"));
			clazz.setClassLearning(rs.getString("class_learning"));
			clazz.setClassCode(rs.getString("class_code"));
			final User user = new User();
			final Profile profile = new Profile();
			profile.setFullName(rs.getString("full_name"));
			user.setProfile(profile);
			clazz.setInstructor(user);
			
			clazz.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getLong("updated_by") != 0) {
				clazz.setUpdatedBy(rs.getLong("updated_by"));
			}
			if (rs.getTimestamp("updated_at") != null) {
				clazz.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			clazz.setIsActive(rs.getBoolean("is_active"));
			clazz.setVer(rs.getInt("ver"));
			clazzez.add(clazz);
		}
		return clazzez;
	}

	@Override
	public List<Clazz> getByInstructor(Long instructorId) throws SQLException {
		final String sql = "SELECT * FROM t_class tc "
				+ "INNER JOIN t_users tu ON tu.id = tc.instructor_id "
				+ "WHERE tu.id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, instructorId);
		final ResultSet rs = ps.executeQuery();
		List<Clazz> clazzez = new ArrayList<>();
		
		while(rs.next()) {	
			final Clazz clazz = new Clazz();
			clazz.setId(rs.getLong("id"));
			clazz.setClassLearning(rs.getString("class_learning"));
			clazz.setClassCode(rs.getString("class_code"));
			
			clazz.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getLong("updated_by") != 0) {
				clazz.setUpdatedBy(rs.getLong("updated_by"));
			}
			if (rs.getTimestamp("updated_at") != null) {
				clazz.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			clazz.setIsActive(rs.getBoolean("is_active"));
			clazz.setVer(rs.getInt("ver"));
			clazzez.add(clazz);
		}
		return clazzez;
	}

	@Override
	public Clazz insert(Clazz clazz) throws SQLException {
		final String sql = "INSERT INTO t_class(class_code, class_learning,class_description,instructor_id,photo_id,created_by, created_at, is_active, ver) VALUES (?,?,?,?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, clazz.getClassCode());
		ps.setString(2, clazz.getClassLearning());
		ps.setString(3, clazz.getClassDescription());
		ps.setLong(4, clazz.getInstructor().getId());
		ps.setLong(5, clazz.getPhoto().getId());
		ps.setLong(6, clazz.getCreatedBy());
		ps.setTimestamp(7, Timestamp.valueOf(clazz.getCreatedAt()));
		ps.setBoolean(8, clazz.getIsActive());
		ps.setInt(9, clazz.getVer());	
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {	
			clazz.setId(rs.getLong("id"));
		}
		conn.commit();
		return clazz;
	}

}
