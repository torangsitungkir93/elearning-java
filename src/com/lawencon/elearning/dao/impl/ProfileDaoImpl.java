package com.lawencon.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.lawencon.elearning.dao.ProfileDao;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Profile;

public class ProfileDaoImpl implements ProfileDao{
	private final Connection conn;
	
	public ProfileDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Profile getById(Long id) throws SQLException {
		final String sql = "SELECT * FROM t_profile WHERE id = ? ";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();

		Profile profile = new Profile();
		if(rs.next()) {
			profile.setId(rs.getLong("id"));
			profile.setFullName(rs.getString("full_name"));
			profile.setPhone(rs.getString("phone"));
			
			final File file = new File();
			file.setId(rs.getLong("file_id"));
			profile.setProfilePhoto(file);
			
			profile.setCreatedBy(rs.getLong("created_by"));
			profile.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if(rs.getLong("updated_at")!=0) {
				profile.setUpdatedBy(rs.getLong("updated_by"));
			}
			if(rs.getTimestamp("updated_at")!=null) {
				profile.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			profile.setIsActive(rs.getBoolean("is_active"));
			profile.setVer(rs.getInt("ver"));
		}
		return profile;
	}

	@Override
	public Profile insert(Profile profile) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile update(Profile profile) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
