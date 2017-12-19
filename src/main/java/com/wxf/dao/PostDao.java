package com.wxf.dao;


import com.wxf.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostDao {
	Post findPostById(Integer id);
}
