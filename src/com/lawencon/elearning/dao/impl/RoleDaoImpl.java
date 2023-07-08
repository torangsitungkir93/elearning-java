package com.lawencon.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lawencon.elearning.dao.RoleDao;
import com.lawencon.elearning.model.Role;

public class RoleDaoImpl implements RoleDao{
	private final Connection conn;
	
	public RoleDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Role> getAll() throws SQLException {
		final String sql = "SELECT * FROM t_role";
		final PreparedStatement ps = conn.prepareStatement(sql);
		final ResultSet rs = ps.executeQuery();
		
		final List<Role> roles = new ArrayList<>();
		while(rs.next()) {
			final Role role = new Role();
			role.setId(rs.getLong("id"));
			role.setCode_role(rs.getString("code_role"));
			role.setName_role(rs.getString("name_role"));
			roles.add(role);
		}
		return roles;
	}
}
