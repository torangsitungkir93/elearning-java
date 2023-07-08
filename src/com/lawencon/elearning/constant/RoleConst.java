package com.lawencon.elearning.constant;

public enum RoleConst {
	ADMIN("ADMIN", "Super Admin"),
	INSTRUCTOR("LECTR", "Instructor"),
	STUDENT("STDNT", "Student");

	private final String code;
	private final String name;

	RoleConst(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	public String getRoleCode() {
		return this.code;
	}
	public String getNameRole() {
		return this.name;
	}
}
