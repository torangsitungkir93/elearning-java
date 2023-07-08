package com.lawencon.elearning.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lawencon.elearning.config.DbConfig;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.dao.ProfileDao;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.dao.impl.FileDaoImpl;
import com.lawencon.elearning.dao.impl.ProfileDaoImpl;
import com.lawencon.elearning.dao.impl.UserDaoImpl;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Profile;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.UserService;


public class UserServiceImpl implements UserService{
	private final UserDao userDao;
	private final ProfileDao profileDao;
	private final FileDao fileDao;
	private final Connection conn = DbConfig.conn();
	
	public UserServiceImpl() throws SQLException{	
		userDao = new UserDaoImpl(conn);
		profileDao = new ProfileDaoImpl(conn);
		fileDao = new FileDaoImpl(conn);
	}

	@Override
	public List<User> getAll() throws SQLException {
		final List<User> users = userDao.getAll();
		return users;
	}

	@Override
	public List<User> getByRoleCode(String roleCode) throws SQLException {
		final List<User> users = userDao.getByRoleCode(roleCode);
		return users;
	}

	@Override
	public User insert(User user, Profile profile, File file) throws SQLException {
		User userResult = null;
		try {
			final File fileResult = fileDao.insert(file);
			profile.setProfilePhoto(fileResult);
			final Profile profileResult = profileDao.insert(profile);
			user.setProfile(profileResult);
			userResult = userDao.insert(user,profileResult,fileResult);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return userResult;
	}

	@Override
	public User update(User user, Profile profile) throws SQLException {
		User userResult = null;
		try {
			final Profile profileResult = profileDao.insert(profile);
			user.setProfile(profileResult);
			// not finished yet
			userResult = userDao.insert(userResult, profileResult, null);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return userResult;
	}

	@Override
	public Profile changePhoto(Long profileId, File file) throws SQLException {
		Profile profileResult = null;
		try {
			final Profile profile = profileDao.getById(profileId);
			final File fileResult = fileDao.insert(file);
			profile.setProfilePhoto(fileResult);
			profileResult = profileDao.update(profile);
			fileDao.deleteById(profile.getProfilePhoto().getId());
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return profileResult;
	}

	@Override
	public User login(String email, String userPassword) throws SQLException {
		final User user = userDao.getByUserNameAndPassword(email, userPassword);
		return user;
	}
}
