package com.wxf.service.Impl;


import com.wxf.dao.NoteBookDao;
import com.wxf.dao.NoteDao;
import com.wxf.dao.UserDao;
import com.wxf.domain.Note;
import com.wxf.domain.NoteBook;
import com.wxf.domain.User;
import com.wxf.service.PasswordException;
import com.wxf.service.UserNameException;
import com.wxf.service.UserNotFoundException;
import com.wxf.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.UUID;

@Service("userService")
public class UserServiceImpl implements UserService {
	private final static String SALT ="还在吃屎吗";
	@Resource(name="userDao")
	private UserDao userDao;
	@Resource
	private NoteBookDao noteBookDao;
	@Resource
	private NoteDao noteDao;

	@Value("${jdbc.salt}")
	private String salt;
	public User login(String name, String password) throws UserNotFoundException, PasswordException {
		System.out.println("login");
		
		
		User user = userDao.findByName(name.trim());
		if(password==null || password.trim().isEmpty()){
			throw new PasswordException("密码不能为空");
		}
		if(name==null || name.trim().isEmpty()){
			throw new UserNotFoundException("用户名不能为空");
		}
		
		if(user==null){
			throw new UserNotFoundException("用户不存在");
		}
		String pwd = DigestUtils.md5Hex(password+SALT);
		
		if(pwd.equals(user.getPassword())){
			return user;
		}
		throw new PasswordException("密码错误");
		
	}
	@Transactional
	public User regist(String name, String nick, String password, String confirm)
			throws UserNameException, PasswordException {
		//检查name，不能重复
		if(name==null || name.trim().isEmpty()){
			throw new UserNameException("用户名不能为空");
		}
		User u = userDao.findByName(name);
		if(u!=null){
			throw new UserNameException("该用户名已注册");
		}
		//检查密码
		if(password==null || password.trim().isEmpty()){
			throw new PasswordException("密码不能为空");
		}
		if(confirm==null || confirm.trim().isEmpty()){
			throw new PasswordException("密码不能为空");
		}
		if(!password.equals(confirm)){
			throw new PasswordException("前后密码不一致");
		}
		//检查nick
		if(nick==null || nick.trim().isEmpty()){
			nick=name;
		}
		User user = new User();
		String id = UUID.randomUUID().toString();
		String token = "";
		user.setId(id);
		user.setName(name);
		user.setNick(nick);
		password = DigestUtils.md5Hex(password+SALT);
		user.setPassword(password);
		user.setToken(token);
		int n = userDao.addUser(user);
		if(n!=1){
			throw new RuntimeException("添加失败");
		}

		String noteBookId = UUID.randomUUID().toString();
		String noteBookName = "笔记本";
		Timestamp notebookCreateTime = new Timestamp(System.currentTimeMillis());
		NoteBook noteBook = new NoteBook();
		noteBook.setUserId(id);
		noteBook.setName(noteBookName);
		noteBook.setCreateTime(notebookCreateTime);
		noteBook.setNoteBookId(noteBookId);
		int nBokk = noteBookDao.initUser(noteBook);
		if(nBokk!=1){
			throw new RuntimeException("注册失败，初始化笔记本失败");
		}

		Note note = new Note();
		String title="新建笔记";
		String body="开始使用笔记本";
		String noteId = UUID.randomUUID().toString();
		Timestamp noteTime = new Timestamp(System.currentTimeMillis());
		note.setUserId(id);
		note.setNotebookId(noteBookId);
		note.setNoteId(noteId);
		note.setCreateTime(noteTime);
		note.setLastTime(noteTime);
		note.setTitle(title);
		note.setBody(body);
		int nNote = noteDao.addNote(note);
		return user;
	}

	public int changePwd(String userId,String password,String newPassword,String finalPassword) throws UserNotFoundException {
		if(userId==null || userId.trim().isEmpty()){
			throw new UserNotFoundException("该用户不存在");
		}
		User user = userDao.findById(userId);
		password= DigestUtils.md5Hex(password+SALT);
		if(!user.getPassword().equals(password)){
			throw new UserNotFoundException("密码错误");
		}
		if(!newPassword.equals(finalPassword)){
			throw new UserNotFoundException("前后密码不一致");
		}
		newPassword=DigestUtils.md5Hex(newPassword+SALT);
		int i=userDao.changePwd(userId,newPassword);
		return i;
	}

}
