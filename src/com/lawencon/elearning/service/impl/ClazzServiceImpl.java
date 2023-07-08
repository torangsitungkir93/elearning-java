package com.lawencon.elearning.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.elearning.config.DbConfig;
import com.lawencon.elearning.dao.ClazzDao;
import com.lawencon.elearning.dao.FileDao;
import com.lawencon.elearning.dao.ProfileDao;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.dao.impl.ClazzDaoImpl;
import com.lawencon.elearning.dao.impl.FileDaoImpl;
import com.lawencon.elearning.dao.impl.ProfileDaoImpl;
import com.lawencon.elearning.dao.impl.UserDaoImpl;
import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.ClazzService;
import com.lawencon.elearning.util.GeneratorUtil;

public class ClazzServiceImpl implements ClazzService{
	private final UserDao userDao;
	private final FileDao fileDao;
	private final ClazzDao clazzDao;
	private final Connection conn = DbConfig.conn();
	
	public ClazzServiceImpl() throws SQLException{	
		userDao = new UserDaoImpl(conn);
		fileDao = new FileDaoImpl(conn);
		clazzDao = new ClazzDaoImpl(conn);
	}
	
	@Override
	public List<Clazz> getAll() throws SQLException {
		final List<Clazz> clazz = clazzDao.getAll();
		return clazz;
	}

	@Override
	public List<Clazz> getByInstructor(Long instructorId) throws SQLException {
		final List<Clazz> clazz = clazzDao.getByInstructor(instructorId);
		return clazz;
	}

	@Override
	public Clazz createClazz(String classLearning, String classDescription, Long userId, String fileName, Long createdById)
			throws SQLException {
		final Clazz clazz = new Clazz();
		final User user = new User();
		
		try {
			
			user.setId(userId);
			clazz.setInstructor(user);
			
			final File file = new File();
			file.setFiles(fileName);
			final int lastDot = fileName.lastIndexOf(".");
			String fileExt = fileName.substring(lastDot + 1);
			file.setExt(fileExt);
			file.setCreatedBy(createdById);
			final LocalDateTime timeNow = LocalDateTime.now();
			file.setCreatedAt(timeNow);
			file.setIsActive(true);
			file.setVer(0);
			fileDao.insert(file);
			
			clazz.setPhoto(file);
			
			clazz.setClassCode(GeneratorUtil.generateRandomCode());
			clazz.setClassLearning(classLearning);
			clazz.setClassDescription(classDescription);

			clazz.setCreatedBy(createdById);
			clazz.setCreatedAt(timeNow);
			clazz.setIsActive(true);
			clazz.setVer(0);
			
			clazzDao.insert(clazz);
			conn.commit();
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return clazz;
	}

}
