package com.wxf.service;

import com.wxf.domain.Note;

import java.util.List;
import java.util.Map;

public interface NoteService {
	List<Map<String, Object>> Note(String noteId) throws NoteIdNotFoundException;
	Note addnote(String title, String userId, String notebookId)throws NoteException;
	void moveNote(String notebookId, String noteId)throws NoteException;
	int deleteNotes(String... noteIds)throws NoteException;
	boolean addStras(String userId, int stars);
	void saveNote(String noteId, String body, String title)throws NoteException;
	void tombstoneNote(String noteId) throws NoteException;
	List<Map<String,Object>> showTrash(String userId)throws NoteException;
	List<Map<String,Object>> searchNote(String userId, String searchTxt);
}
