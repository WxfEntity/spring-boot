package com.wxf.service.Impl;


import com.wxf.dao.NoteBookDao;
import com.wxf.dao.UserDao;
import com.wxf.domain.User;
import com.wxf.service.NoteBookService;
import com.wxf.service.NotebookIdNotFoundException;
import com.wxf.service.UserNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("noteBookService")
public class NotebooksImpl implements NoteBookService {
	@Resource
	private NoteBookDao notebookDao;
	@Resource 
	private UserDao userDao;


	private int pageSize=10;

	public List<Map<String, Object>> listNotebooks(String userId) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID不能为空");
		}
		User user = userDao.findById(userId);
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		return notebookDao.findNoteBookByUserId(userId);
	}

	public List<Map<String, Object>> listNote(String notebookId) throws NotebookIdNotFoundException {
		if(notebookId==null || notebookId.trim().isEmpty() ){
			throw new NotebookIdNotFoundException("笔记本ID不能为空");
		}
		List<Map<String, Object>> list = notebookDao.findNoteByNotebookId(notebookId);
		return list;
	}

	public List<Map<String, Object>> listNotebooks(String userId, Integer page)
			throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID不能为空");
		}
		User user = userDao.findById(userId);
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		if(page==null){
			page=0;
		}
		
		int start = page*pageSize;
		String table="cn_notebook";
		return notebookDao.findNoteBookByPage(userId, start, pageSize, table);
	}

	public Integer addNoteBook(String userId,String name) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("ID不能为空");
		}
		User user = userDao.findById(userId);
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		String noteBookId = UUID.randomUUID().toString();
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		Integer i = notebookDao.addNoteBook(userId,noteBookId,name,createTime);
		return null;
	}

}
