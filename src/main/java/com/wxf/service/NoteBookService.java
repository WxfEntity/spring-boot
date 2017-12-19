package com.wxf.service;

import java.util.List;
import java.util.Map;

public interface NoteBookService {
	List<Map<String, Object>> listNotebooks(String userId) throws UserNotFoundException;
	List<Map<String, Object>> listNote(String notebookId) throws NotebookIdNotFoundException;
	List<Map<String, Object>> listNotebooks(String userId, Integer page) throws UserNotFoundException;
	Integer addNoteBook(String userId, String name)throws UserNotFoundException;
}
