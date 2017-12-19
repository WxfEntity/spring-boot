package com.wxf.domain;

import java.io.Serializable;

public class Stars implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3979251483672508121L;
	private String id;
	private String userId;
	private Integer stars;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	public Stars(String id, String userId, Integer stars) {
		super();
		this.id = id;
		this.userId = userId;
		this.stars = stars;
	}
	public Stars() {
		super();
		
	}
	
}
