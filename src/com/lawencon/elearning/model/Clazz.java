package com.lawencon.elearning.model;

public class Clazz extends BaseModel{
	private String classCode;
	private String classLearning;
	private String classDescription;
	private User instructor;
	private File photo;
	public String getClassCode() {
		return classCode;
	}
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}
	public String getClassLearning() {
		return classLearning;
	}
	public void setClassLearning(String classLearning) {
		this.classLearning = classLearning;
	}
	public String getClassDescription() {
		return classDescription;
	}
	public void setClassDescription(String classDescription) {
		this.classDescription = classDescription;
	}
	public User getInstructor() {
		return instructor;
	}
	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}
	public File getPhoto() {
		return photo;
	}
	public void setPhoto(File photo) {
		this.photo = photo;
	}
}
