package com.lawencon.elearning.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.lawencon.elearning.dao.ClassEnrollmentDao;
import com.lawencon.elearning.model.ClassEnrollment;
import com.lawencon.elearning.model.Clazz;
import com.lawencon.elearning.model.Profile;
import com.lawencon.elearning.model.User;

public class ClassEnrollmentDaoImpl implements ClassEnrollmentDao{
	private final Connection conn;
	
	public ClassEnrollmentDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<ClassEnrollment> getAll() throws SQLException {
		return null;
	}

	@Override
	public List<ClassEnrollment> getAllByStudent(Long studentId) throws SQLException {
		final String sql = "SELECT * FROM t_class_enrollment tce "
				+ "INNER JOIN t_class tc ON tce.class_id = tc.id "
				+ "INNER JOIN t_users tu ON tc.instructor_id = tu.id "
				+ "INNER JOIN t_profile tp ON tp.id = tu.profile_id "
				+ "WHERE tce.student_id = ?";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, studentId);
		final ResultSet rs = ps.executeQuery();
		List<ClassEnrollment> classEnrollments = new ArrayList<>();
		
		while(rs.next()) {	
			final ClassEnrollment classEnrollment = new ClassEnrollment();
			classEnrollment.setId(rs.getLong("id"));
			
			final Clazz clazz = new Clazz();
			clazz.setClassLearning(rs.getString("class_learning"));
			
			
			final User user = new User();
			final Profile profile = new Profile();
			profile.setFullName(rs.getString("full_name"));
			user.setProfile(profile);
			clazz.setInstructor(user);
			classEnrollment.setClazz(clazz);
			
			classEnrollment.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
			if (rs.getLong("updated_by") != 0) {
				classEnrollment.setUpdatedBy(rs.getLong("updated_by"));
			}
			if (rs.getTimestamp("updated_at") != null) {
				classEnrollment.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
			}
			classEnrollment.setIsActive(rs.getBoolean("is_active"));
			classEnrollment.setVer(rs.getInt("ver"));
			classEnrollments.add(classEnrollment);
		}
		return classEnrollments;
	}

	@Override
	public ClassEnrollment insert(ClassEnrollment classEnrollment) throws SQLException {
		final String sql = "INSERT INTO t_class_enrollment(student_id, class_id,created_by, created_at, is_active, ver) VALUES (?,?,?,?,?,?) RETURNING *";
		final PreparedStatement ps = conn.prepareStatement(sql);
		ps.setLong(1, classEnrollment.getStudent().getId());
		ps.setLong(2, classEnrollment.getClazz().getId());
		ps.setLong(3, classEnrollment.getCreatedBy());
		ps.setTimestamp(4, Timestamp.valueOf(classEnrollment.getCreatedAt()));
		ps.setBoolean(5, classEnrollment.getIsActive());
		ps.setInt(6, classEnrollment.getVer());	
		final ResultSet rs = ps.executeQuery();
		
		if(rs.next()) {	
			classEnrollment.setId(rs.getLong("id"));
		}
		conn.commit();
		return classEnrollment;
	}

}
