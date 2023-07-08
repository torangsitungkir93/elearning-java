package com.lawencon.elearning.dao;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Profile;
import com.lawencon.elearning.model.User;

public interface UserDao {
	List<User> getAll() throws SQLException;
	List<User> getByRoleCode(String roleCode) throws SQLException;
	User insert(User user, Profile profile, File file) throws SQLException;
	User update(User user) throws SQLException;
	User getByUserNameAndPassword(String email, String passwordUser) throws SQLException;
}
