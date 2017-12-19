package com.wxf.controller;


import com.wxf.service.*;

import java.io.Serializable;


public class JsonResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int SUCCESS=0;
	private static final int ERROR=1;
	
	private int stata;
	private String message;
	private Object data;
	public JsonResult(Throwable e) {
		this.stata=ERROR;
		this.message=e.getMessage();
	}
	public JsonResult(Object data) {
		this.stata=SUCCESS;
		this.data=data;
	}
	public JsonResult(String error) {
		this.stata=ERROR;
		this.message=error;
	}
	public JsonResult() {
		
		
	}
	public JsonResult(int i, UserNotFoundException e) {
		this.stata=i;
		this.message = e.getMessage();
	}
	public JsonResult(int i, PasswordException e) {
		this.stata=i;
		this.message = e.getMessage();
	}
	public JsonResult(int i, UserNameException e) {
		this.stata=i;
		this.message = e.getMessage();
	}
	public JsonResult(int i, NotebookIdNotFoundException e) {
		this.stata=i;
		this.message = e.getMessage();
	}
	
	public JsonResult(int i, NoteIdNotFoundException e) {
		this.stata=i;
		this.message = e.getMessage();
	}
	public JsonResult(int i, NoteException e) {
		this.stata=i;
		this.message = e.getMessage();
	}
	public int getStata() {
		return stata;
	}
	public void setStata(int stata) {
		this.stata = stata;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "JsonResult [stata=" + stata + ", message=" + message + ", data=" + data + "]";
	}
	
}
