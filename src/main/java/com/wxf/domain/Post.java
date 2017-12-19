package com.wxf.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Post implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5428345928265054254L;
	private Integer id;
	private String title;
	private Person person;
	List<Comment> comment = new ArrayList<Comment>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public List<Comment> getComment() {
		return comment;
	}
	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Post() {
		super();
	}
	public Post(Integer id, String title, Person person, List<Comment> comment) {
		super();
		this.id = id;
		this.title = title;
		this.person = person;
		this.comment = comment;
	}
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", person=" + person + ", comment=" + comment + "]";
	}
	
	
}
