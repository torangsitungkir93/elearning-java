package com.lawencon.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.model.File;

public class FileDaoImpl implements FileDao{
	private final Connection conn;
	
	public FileDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public File getById(Long id) throws SQLException {
		final String sql = "SELECT * FROM t_file WHERE id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		final ResultSet rs = ps.executeQuery();
		File file = null;
		
		if(rs.next()) {	
			file = new File();
			file.setId(rs.getLong("id"));
			file.setExt(rs.getString("ext"));
			file.setFiles(rs.getString("files"));
			file.setCreatedBy(rs.getLong("created_by"));
			file.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getLong("updated_by") != 0) {
				file.setUpdatedBy(rs.getLong("updated_by"));
			}
			if (rs.getTimestamp("updated_at") != null) {
				file.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			file.setIsActive(rs.getBoolean("is_active"));
			file.setVer(rs.getInt("ver"));
		}
		return file;
	}

	@Override
	public File insert(File file) throws SQLException {
		final String sql = "INSERT INTO t_file(ext, files, created_by, created_at, is_active, ver) VALUES (?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, file.getExt());
		ps.setString(2, file.getFiles());
		ps.setLong(3, file.getCreatedBy());
		ps.setTimestamp(4, Timestamp.valueOf(file.getCreatedAt()));
		ps.setBoolean(5, file.getIsActive());
		ps.setInt(6, file.getVer());	
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {	
			file.setId(rs.getLong("id"));
		}
		conn.commit();
		return file;
	}

	@Override
	public boolean deleteById(Long id) throws SQLException {
		final String sql = "DELETE FROM t_file WHERE id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, id);
		int result = ps.executeUpdate();
		return result > 0;
	}

}
