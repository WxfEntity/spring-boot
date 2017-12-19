package com.wxf.service.Impl;


import com.wxf.dao.NoteBookDao;
import com.wxf.dao.NoteDao;
import com.wxf.dao.StarsDao;
import com.wxf.dao.UserDao;
import com.wxf.domain.Note;
import com.wxf.domain.Stars;
import com.wxf.domain.User;
import com.wxf.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("noteService")
public class NoteServiceImpl implements NoteService {
	private final static int SUCCESS=0;
	private final static int FAIL=1;
	@Resource
	private NoteDao noteDao;
	@Resource
	private NoteBookDao notebookDao;
	@Resource
	private UserDao userDao;
	@Resource
	private StarsDao starDao;
	
	public List<Map<String, Object>> Note(String noteId) throws NoteIdNotFoundException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteIdNotFoundException("笔记ID不能为空");
		}
		List<Map<String, Object>> list=noteDao.findNoteByNoteId(noteId);
		if(list==null){
			throw new NoteIdNotFoundException("笔记ID错误");
		}
		return list;
	}

	public Note addnote(String title, String userId, String notebookId) throws NoteException {
		Note note = new Note();
		String noteId=UUID.randomUUID().toString();
		Integer statusId=1;
		Timestamp createTime = new Timestamp(System.currentTimeMillis());
		Timestamp lastTime=new Timestamp(System.currentTimeMillis());
		String body="";
		String typeId="";
		note.setBody(body);
		note.setCreateTime(createTime);
		note.setLastTime(lastTime);
		note.setNotebookId(notebookId);
		note.setNoteId(noteId);
		note.setStatusId(statusId);
		note.setTitle(title);
		note.setTypeId(typeId);
		note.setUserId(userId);
		int i = noteDao.addNote(note);
		if(i!=1){
			throw new NoteException("添加笔记失败");
		}
		return note;
	}

	public void moveNote(String notebookId, String noteId) throws NoteException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteIdNotFoundException("笔记ID不能为空");
		}
		if(notebookId==null || notebookId.trim().isEmpty()){
			throw new NotebookIdNotFoundException("笔记本ID不能为空");
		}
		List<Map<String, Object>> list=noteDao.findNoteByNoteId(noteId);
		if(list==null){
			throw new NoteIdNotFoundException("笔记ID错误");
		}
		
		int i = noteDao.upNotebookIdbyNoteId(notebookId, noteId);
		if(i!=1){
			throw new NoteException("移动笔记失败");
		}
		
	}

	//批量删除功能，但是用不上
	public int deleteNotes(String... noteIds) throws NoteException {
		if(noteIds.length<=0){
			throw new NoteException("笔记ID不能为空");
		}

		int i=noteDao.deleteNotes(noteIds);
		if(i==0){
			throw new NoteException("删除笔记失败");
		}else{
			return SUCCESS;
		}

	}
	@Transactional
	public boolean addStras(String userId, int stars) 
			throws UserNotFoundException {
		if(userId==null||userId.trim().isEmpty()){
			throw new UserNotFoundException("ID空");
		}
		User user=userDao.findById(userId);
		if(user==null){
			throw new UserNotFoundException("木有人");
		}
		//检查是否已经有星了
		Stars st=starDao.findStarsByUserId(userId);
		if(st==null){//如果没有星星
			String id = UUID.randomUUID().toString();
			st = new Stars(id, userId, stars);
			int n = starDao.addStras(st);
			if(n!=1){
				throw new RuntimeException("失败");
			}
		}else{//如果有星星,就在现有星星数量上增加
			int n = st.getStars()+stars;
			if(n<0){
				// n = 0; 
				throw new RuntimeException("扣分太多!");
			}
			st.setStars(n);
			n = starDao.updateStars(st);
			if(n!=1){
				throw new RuntimeException("失败");
			}
		}
		return true;
	}

	public void saveNote(String noteId, String body, String title) {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteIdNotFoundException("笔记ID不能为空");
		}
		if(title==null || title.trim().isEmpty()){
			throw new NoteIdNotFoundException("标题不能为空");
		}
		int i = noteDao.saveNote(noteId,body,title);
		if(i!=1){
			throw new NoteException("保存笔记失败，请重新保存！");
		}

	}

	public void tombstoneNote(String noteId) throws NoteException {
		if(noteId==null || noteId.trim().isEmpty()){
			throw new NoteIdNotFoundException("笔记ID不能为空");
		}
		int i = noteDao.tombstoneNote(noteId);
		if(i!=1){
			throw new NoteException("笔记删除失败");
		}

	}

	public List<Map<String, Object>> showTrash(String userId) throws NoteException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("该用户不存在");
		}
		List<Map<String, Object>> list = noteDao.showTombstoneNote(userId);
		return list;
	}

	public List<Map<String, Object>> searchNote(String userId,String searchTxt) {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("该用户不存在");
		}
		List<Map<String, Object>> list = noteDao.searchNotes(userId,searchTxt);
		return list;
	}

}
