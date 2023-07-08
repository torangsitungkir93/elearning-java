package com.lawencon.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Profile;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.model.User;

public class UserDaoImpl implements UserDao{
private final Connection conn;
	
	public UserDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public List<User> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_users";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		final List<User> users = new ArrayList<>();
		
		while(rs.next()) {
			final User user = new User();
			
			user.setId(rs.getLong("id"));
			user.setEmail(rs.getString("email"));
			user.setPasswordUser(rs.getString("password_user"));
			
			final Role role = new Role();
			role.setId(rs.getLong("role_id"));
			user.setRole(role);

			final Profile profile = new Profile();
			profile.setId(rs.getLong("profile_id"));
			user.setProfile(profile);
			
			user.setCreatedBy(rs.getLong("created_by"));
			user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getLong("updated_by") != 0) {
				user.setUpdatedBy(rs.getLong("updated_by"));
			}
			if (rs.getTimestamp("updated_at") != null) {
				user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			user.setIsActive(rs.getBoolean("is_active"));
			user.setVer(rs.getInt("ver"));
			users.add(user);
		}
		return users;
	}

	@Override
	public List<User> getByRoleCode(String roleCode) throws SQLException {
		final List<User> users = new ArrayList<>();
		final String sql = "select * from t_users u inner join t_role r on r.id = u.role_id inner join t_profile tp ON tp.id = u.profile_id where r.code_role = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, roleCode);
		final ResultSet rs = ps.executeQuery();
		
		while(rs.next()) {
			final User user = new User();
			user.setId(rs.getLong("id"));
			user.setEmail(rs.getString("email"));
			user.setPasswordUser(rs.getString("password_user"));
			
			final Role role = new Role();
			role.setId(rs.getLong("role_id"));
			user.setRole(role);

			final Profile profile = new Profile();
			profile.setId(rs.getLong("profile_id"));
			profile.setFullName(rs.getString("full_name"));
			user.setProfile(profile);
			
			user.setCreatedBy(rs.getLong("created_by"));
			user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getLong("updated_by") != 0) {
				user.setUpdatedBy(rs.getLong("updated_by"));
			}
			if (rs.getTimestamp("updated_at") != null) {
				user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			user.setIsActive(rs.getBoolean("is_active"));
			user.setVer(rs.getInt("ver"));
			users.add(user);
		}
		return users;
	}

	@Override
	public User insert(User user, Profile profile, File file) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User update(User user) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getByUserNameAndPassword(String email, String passwordUser) throws SQLException {
		final String sql = "SELECT * FROM t_users u "
				+ "INNER JOIN t_role r ON r.id = u.role_id "
				+ "INNER JOIN t_profile tp ON u.profile_id= tp.id "
				+ "WHERE u.email = ? AND u.password_user = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, email);
		ps.setString(2, passwordUser);
		final ResultSet rs = ps.executeQuery();
		
		final User user = new User();
		
		if(rs.next()) {
			user.setId(rs.getLong("id"));
			user.setEmail(rs.getString("email"));
			user.setPasswordUser(rs.getString("password_user"));
			
			final Role role = new Role();
			role.setId(rs.getLong("id"));
			role.setCode_role(rs.getString("code_role"));
			user.setRole(role);

			final Profile profile = new Profile();
			profile.setId(rs.getLong("id"));
			profile.setFullName(rs.getString("full_name"));
			user.setProfile(profile);
			
			user.setCreatedBy(rs.getLong("created_by"));
			user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getLong("updated_by") != 0) {
				user.setUpdatedBy(rs.getLong("updated_by"));
			}
			if (rs.getTimestamp("updated_at") != null) {
				user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			user.setIsActive(rs.getBoolean("is_active"));
			user.setVer(rs.getInt("ver"));
		}
		return user;
	}
}
