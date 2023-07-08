package com.lawencon.elearning.service;

import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Profile;
import com.lawencon.elearning.model.User;

public interface UserService {
	List<User> getAll() throws SQLException;
	List<User> getByRoleCode(String roleCode) throws SQLException;
	User insert(User user, Profile profile, File file) throws SQLException;
	User update(User user, Profile profile) throws SQLException;
	Profile changePhoto(Long profileId, File file) throws SQLException;
	User login(String userName, String userPassword) throws SQLException;
}
