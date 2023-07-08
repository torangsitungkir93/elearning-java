package com.lawencon.elearning.model;

public class Profile extends BaseModel{
	
	private String fullName;
	private String phone;
	private File profilePhoto;
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public File getProfilePhoto() {
		return profilePhoto;
	}
	public void setProfilePhoto(File profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
