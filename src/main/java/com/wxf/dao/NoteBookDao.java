package com.wxf.dao;

import com.wxf.domain.NoteBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Repository
public interface NoteBookDao {
	List<Map<String, Object>> findNoteBookByUserId(String userId);
	List<Map<String, Object>> findNoteByNotebookId(String notebookId);
	List<Map<String, Object>> NotebookList();
	List<Map<String, Object>> findNoteBookByPage(@Param("userId") String userId, @Param("start") int start,
                                                 @Param("pageSize") int pageSize, @Param("table") String table);
	Integer addNoteBook(@Param("userId") String userId, @Param("noteBookId") String noteBookId, @Param("name") String name, @Param("createTime") Timestamp createTime);
	Integer initUser(NoteBook noteBook);
}
