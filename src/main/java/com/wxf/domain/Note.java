package com.wxf.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name="cn_note")
public class Note  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1961463179958821612L;

	@Id
	@GeneratedValue
	private  Long id;

	@Column(columnDefinition = "varchar(255)  COMMENT '用户ID'",name = "cn_note_id")
	private String userId;
	@Column(name = "cn_notebook_id")
	private String notebookId;
	@Column(name = "note_id")
	private String noteId;
	/**
	 * 回收站状态：0
	 * 正常状态：1
	 */
	@Column(name = "cn_note_status_id")
	private Integer statusId=1;
	@Column(name = "cn_note_type_id")
	private String typeId;
	@Column(name = "cn_note_title",length = 255)
	private String title;
	@Column(name = "cn_note_body")
	private String body;
	@Column(name = "cn_note_create_time",columnDefinition = "datetime COMMENT '创建时间'")
	private Timestamp createTime;
	@Column(name = "cn_note_last_modify_time",columnDefinition = "datetime COMMENT '更新时间'")
	private Timestamp lastTime;

	@Column(columnDefinition = "int COMMENT '点击次数'",name = "cn_note_watch_number")
	private Integer watchNumber=0;

	@Column(columnDefinition = "int COMMENT '是否生成博客'",name = "cn_note_is_blog")
	private boolean isBlog =false;

	public boolean getIsBlog() {
		return isBlog;
	}

	public void setIsBlog(boolean isBlog) {
		this.isBlog = isBlog;
	}

	public Integer getWatchNumber() {
		return watchNumber;
	}

	public void setWatchNumber(Integer watchNumber) {
		this.watchNumber = watchNumber;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNotebookId() {
		return notebookId;
	}
	public void setNotebookId(String notebookId) {
		this.notebookId = notebookId;
	}
	public String getNoteId() {
		return noteId;
	}
	public void setNoteId(String noteId) {
		this.noteId = noteId;
	}
	public Integer getStatusId() {
		return statusId;
	}
	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public Timestamp getLastTime() {
		return lastTime;
	}
	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}
	@Override
	public String toString() {
		return "Note [userId=" + userId + ", notebookId=" + notebookId + ", noteId=" + noteId + ", statusId=" + statusId
				+ ", typeId=" + typeId + ", title=" + title + ", body=" + body + ", createTime=" + createTime
				+ ", lastTime=" + lastTime + "]";
	}
	
}
