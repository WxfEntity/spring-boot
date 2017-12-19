package com.wxf.service;

import com.wxf.domain.Note;

import java.util.List;

/**
 * Created by TYZ027 on 2017/11/28.
 */
public interface BlogService {
    List<Note> findNoteByTime(String userName);
}
