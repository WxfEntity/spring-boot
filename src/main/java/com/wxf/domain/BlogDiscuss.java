package com.wxf.domain;



import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by TYZ027 on 2017/11/28.
 */
@Entity
@Table(name="blog_discuss")
public class BlogDiscuss implements Serializable{
    @Id
    @GeneratedValue
    private Integer id;

    @Column(columnDefinition = "varchar(255)  COMMENT '笔记Id'",name = "cn_note_id")
    private String noteId;

    @Column(columnDefinition = "dateTime comment'创建时间'",name = "create_time")
    private Timestamp createTime;

    @Column(columnDefinition = "int comment '回复的评论Id'",name = "reply_id")
    private Integer replyId;

    @Column(columnDefinition = "int comment '点赞次数'",name = "like_times")
    private Integer LikeTimes;
}
