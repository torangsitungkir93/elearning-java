package com.lawencon.elearning.model;

public class Forums extends BaseModel{
	private String title;
	private String body;
	private LearningSession learningSession;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public LearningSession getLearningSession() {
		return learningSession;
	}
	public void setLearningSession(LearningSession learningSession) {
		this.learningSession = learningSession;
	}
}
