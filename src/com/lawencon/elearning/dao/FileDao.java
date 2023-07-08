package com.lawencon.elearning.dao;

import java.sql.SQLException;

import com.lawencon.elearning.model.File;

public interface FileDao {
	File getById(Long id) throws SQLException;
	File insert(File file) throws SQLException;
	boolean deleteById(Long id) throws SQLException;
}
