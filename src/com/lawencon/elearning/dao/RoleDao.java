package com.lawencon.elearning.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.Role;

public interface RoleDao {
	List<Role> getAll() throws SQLException;
}
