package com.wxf.controller;


import com.wxf.domain.Note;
import com.wxf.service.NoteException;
import com.wxf.service.NoteIdNotFoundException;
import com.wxf.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController{
	@Autowired
	private NoteService noteService;
	
	@RequestMapping("/select.do")
	@ResponseBody
	public JsonResult note(String noteId){
		List<Map<String, Object>> list =noteService.Note(noteId);
		return  new JsonResult(list);
	}
	
	@RequestMapping("/addNote.do")
	@ResponseBody
	public JsonResult addNote(String title, String userId, String notebookId){
		Note note = noteService.addnote(title, userId, notebookId);
		return new JsonResult(note);
		
	}
	@RequestMapping("/upNotebook.do")
	@ResponseBody
	public JsonResult showNotebook(String notebookId,String noteId){
		noteService.moveNote(notebookId, noteId);
		return new JsonResult(0);
		
	}
	@RequestMapping("/saveNote.do")
	@ResponseBody
	public JsonResult saveNote(String noteId,String body,String title){
		noteService.saveNote(noteId,body,title);
		return new JsonResult(0);
	}

	@RequestMapping("/deleteNote.do")
	@ResponseBody
	public JsonResult deleteNote(String noteId){
		noteService.tombstoneNote(noteId);
		return new JsonResult(0);
	}
	@RequestMapping("/showTrashNote.do")
	@ResponseBody
	public JsonResult showTrash(String userId){
		List<Map<String, Object>> list = noteService.showTrash(userId);
		return new JsonResult(list);
	}

	/**
	 * 下载功能
	 * @param noteId
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadNote.do")
	@ResponseBody
	public JsonResult download(String noteId,HttpServletResponse response) throws IOException {
		/*try {
			*//*System.out.print("212222");
			List<Map<String, Object>> list =noteService.Note(noteId);
			Map<String,Object> map= list.get(0);
			XwpfTUtil xwpfTUtil = new XwpfTUtil();
			XWPFDocument doc;
			doc = new XWPFDocument(getClass().getClassLoader().getResourceAsStream("note.docx"));
			xwpfTUtil.replaceInPara(doc,map);
			System.out.println(doc);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition","attachment;filename="+"笔记"+".docx");
			OutputStream os = response.getOutputStream();
			doc.write(os);
			os.flush();
			xwpfTUtil.close(os);*//*
			return new JsonResult(0);
		}catch (IOException e){

			return new JsonResult(1);
		}*/
		return null;
	}

	/**
	 * 搜索笔记功能
	 * @param userId
	 * @param searchTxt
	 * @return
	 */
	@RequestMapping("search.do")
	@ResponseBody
	public JsonResult searchNote(String userId,String searchTxt){
		System.out.println(searchTxt);
		List<Map<String, Object>> list= noteService.searchNote(userId,searchTxt);
		return new JsonResult(list);
	}
	@ExceptionHandler(NoteException.class)
	@ResponseBody
	public JsonResult NoteException(NoteException e){
		e.printStackTrace();
		
		return new JsonResult(7,e);
		
	}
	
	
	@ExceptionHandler(NoteIdNotFoundException.class)
	@ResponseBody
	public JsonResult NoteIdException(NoteIdNotFoundException e){
		e.printStackTrace();
		return new JsonResult(6,e);
		
	}
}
