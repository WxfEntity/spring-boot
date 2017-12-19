package com.wxf.dao;


import com.wxf.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao {
	int addPerson(Person person);
}
