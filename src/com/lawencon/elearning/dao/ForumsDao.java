package com.lawencon.elearning.dao;

import java.sql.SQLException;

import com.lawencon.elearning.model.Forums;

public interface ForumsDao {
	Forums insert(Forums forum) throws SQLException;
}
