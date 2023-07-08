package com.lawencon.elearning.model;

public class ClassEnrollment extends BaseModel{
	private User student;
	private Clazz clazz;
	
	public User getStudent() {
		return student;
	}
	public void setStudent(User student) {
		this.student = student;
	}
	public Clazz getClazz() {
		return clazz;
	}
	public void setClazz(Clazz clazz) {
		this.clazz = clazz;
	}
	
}
