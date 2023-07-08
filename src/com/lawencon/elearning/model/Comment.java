package com.lawencon.elearning.model;

public class Comment {
	private User user;
	private Forums forum;
	private String message;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Forums getForum() {
		return forum;
	}
	public void setForum(Forums forum) {
		this.forum = forum;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
