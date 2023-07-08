package com.lawencon.elearning.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.elearning.config.DbConfig;
import com.lawencon.elearning.dao.ClassEnrollmentDao;
import com.lawencon.elearning.dao.ClazzDao;
import com.lawencon.elearning.dao.UserDao;
import com.lawencon.elearning.dao.impl.ClassEnrollmentDaoImpl;
import com.lawencon.elearning.dao.impl.ClazzDaoImpl;
import com.lawencon.elearning.dao.impl.UserDaoImpl;
import com.lawencon.elearning.model.ClassEnrollment;
import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.ClassEnrollmentService;
import com.lawencon.elearning.util.GeneratorUtil;

public class ClassEnrollmentServiceImpl implements ClassEnrollmentService{
	private final UserDao userDao;
	private final ClazzDao clazzDao;
	private final ClassEnrollmentDao classEnrollmentDao;
	private final Connection conn = DbConfig.conn();
	
	public ClassEnrollmentServiceImpl() throws SQLException{	
		userDao = new UserDaoImpl(conn);
		clazzDao = new ClazzDaoImpl(conn);
		classEnrollmentDao = new ClassEnrollmentDaoImpl(conn);
	}
	
	
	@Override
	public List<ClassEnrollment> getAll() throws SQLException {
		final List<ClassEnrollment> classEnrollments = classEnrollmentDao.getAll();
		return classEnrollments;
	}

	@Override
	public List<ClassEnrollment> getAllByStudent(Long studentId) throws SQLException {
		final List<ClassEnrollment> classEnrollments = classEnrollmentDao.getAllByStudent(studentId);
		return classEnrollments;
	}

	@Override
	public ClassEnrollment createClassEnroll(Long studentId, Long clazzId,Long createdById) throws SQLException {
		final Clazz clazz = new Clazz();
		final User user = new User();
		final ClassEnrollment classEnrollment = new ClassEnrollment();
		
		try {
			user.setId(studentId);
			classEnrollment.setStudent(user);
			
			clazz.setId(clazzId);
			classEnrollment.setClazz(clazz);
			
			classEnrollment.setCreatedBy(createdById);
			final LocalDateTime timeNow = LocalDateTime.now();
			classEnrollment.setCreatedAt(timeNow);
			classEnrollment.setIsActive(true);
			classEnrollment.setVer(0);
			
			classEnrollmentDao.insert(classEnrollment);
			conn.commit();
		}catch(Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			}catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return classEnrollment;
	}


}
