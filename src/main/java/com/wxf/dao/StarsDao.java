package com.wxf.dao;


import com.wxf.domain.Stars;

public interface StarsDao {
	Stars findStarsByUserId(String userId);
	int addStras(Stars stars);
	int updateStars(Stars stars);
}
