var SUCCESS=0;
var ERROR=1;
$(function () {
	//在document对象中存储了初始的页号
	$(document).data('page',0);
	
	//var userId = getCookie('userId');
	//console.log(userId);
	
	//网页加载以后，立即读取笔记本列表
	//loadNotebooks();
	loadPageNotebooks();
	$('#notebook-list').on('click','.more',loadPageNotebooks);
	//on()方法绑定事件可以区别事件源
	//click() 方法绑定时间，无法区别事件源
	//绑定笔记本列表区域的点击事件

	$('#notebook-list').on('click','li',showNotes);

	$('#notelist').on('click','li',showTitles);

	//添加笔记
	$('#notelist').on('click','#add_note',showAddNoteDialog);
	$('#can').on('click','.create_note',addNote);
	$('#can').on('click','.close,.cancel',closeDialog);

	//创建笔记本notebook-list
    $('#notebook-list').on('click','#add_notebook',showAddNoteBookDialog);
    $('#bookcan').on('click','.create_notebook',addNoteBook);
    $('#bookcan').on('click','.close,.cancel',closeNoteBookDialog);

	//绑定笔记子菜单的触发事件
	$('#notelist').on('click','.btn-note-menu',showNoteMenu);
	$(document).click(hideNoteMenu);

	//绑定移动笔记的触发事件
	$('#notelist').on('click','.btn_move',showMoveNote);
	$('#note-move').on('click','.close,.cancel',hideMoveNote);
	//选定笔记本，绑定数据
	$('#can').on('click', '.sure', moveNote);
	//删除笔记（逻辑删除，存放到回收站）
    $('#notelist').on('click','.btn_delete',deleteNote);

	//监听回收站按钮被点击
	$('#trash_button').click(showTrashBin);
	//保存功能
	$('#save_note').click(saveNote);

	//下载笔记功能
	$('#download_note').click(downloadNote);
	//搜索功能
    $('#search_note').bind('keypress', function(event) {
        if (event.keyCode == "13") {
            event.preventDefault();
            //回车执行查询
            searchNote();
        }
    });
});

function addNoteBook() {
	var url ="notebook/addNoteBook.do";
	var userId = getCookie('userId');
	var name=$('#input_notebook').val();
	if(name){
        var data={userId:userId,name:name};
        console.log(data);
        $.post(url,data,function (result) {
			if(result.stata==SUCCESS){
                closeNoteBookDialog();
			}
        })
	}else{
		alert("笔记本名字不能为空")
	}
}
//笔记本
function showAddNoteBookDialog() {
    $('#bookcan').load('alert/alert_notebook.html',function(){
        $('.opacity_bg').show();
    });
}
//模糊搜索
function searchNote() {
	var url="note/search.do";
	var userId = getCookie('userId');
	var text= $('#search_note').val();
	if(text){
        var data ={userId:userId,searchTxt:text};
        $.post(url,data,function (result) {
            if(result.stata==SUCCESS){
                var notes = result.data;
                if(notes.length==0){
                    var ul=$('#notelist ul');
                    ul.empty();
                    var li = '<li class="online"><a>查找无结果</a></li>';
                    li =$(li);
                    ul.append(li);
                }else{
                    showNote(notes);
                }
            }else{
            alert("搜索失败");}
        });
	}
}
function downloadNote() {
	var url="note/downloadNote.do";
	var noteId=$(document).data("noteId");
    var data={noteId:noteId};
    console.log("已有请求");
    $.post(url,data,function (result) {

    });
}
function deleteNote() {
	var url="note/deleteNote.do";
	var noteId=$(document).data("noteId");
	var data={noteId:noteId};
	$.post(url,data,function (result) {
		if(result.stata==SUCCESS){
			alert("删除成功");
		}else{
			alert("删除失败")
		}

    })
}
function saveNote() {
	var url="note/saveNote.do";
	var noteId=$(document).data("noteId");

	var body=um.getContent();
    var title=$('#input_note_title').val();
    var data={noteId:noteId,title:title,body:body};
	$.post(url,data,function (result) {
		if(result.stata==SUCCESS){
			$('#save_note').html("已保存");
			setTimeout(function () {
                $('#save_note').html("保存笔记");
            },1000);
		}else{
			alert(result.message);
		}
    });
}

function loadPageNotebooks() {
	var page = $(document).data('page');
	var userId = getCookie('userId');
	//从服务器拉取数据
	var url="notebook/page.do";
	var data={userId:userId,page:page};
	var bookId;
	$.getJSON(url,data,function(result){
		if(result.stata==SUCCESS){
			var notebooks = result.data;
			showPageNotebooks(notebooks,page);
			$(document).data('page',page+1);
            var noteBook = notebooks[0];
            bookId = noteBook.id;
            loadNote(bookId);
		}else{
			alert(result.message);
		}
	});
}
function loadNote(bookId) {
	var url = "notebook/note.do";
	var data={notebookId:bookId};
    $.getJSON(url,data,function(result){
        if(result.stata==SUCCESS){
            if(result.stata==SUCCESS){
                var notes=result.data;
                showNote(notes);
                var noteId = notes[0].noteId;
                loadNoteDetail(noteId);
            }else{
                alert(result.message);
            }
        }
    });
}
function loadNoteDetail(noteId) {
    var url="note/select.do";
    var data={"noteId":noteId};
    $(document).data("noteId",noteId);
    $.getJSON(url,data,function(result){
        if(result.stata==0){
            var notes=result.data;
            showTitle(notes);
        }else{
            alert(result.message);
        }
    });
}
function showPageNotebooks(notebooks,page) {
	var ul = $('#notebook-list ul');
	if(page==0){//第一页的时候情况ul的li
		ul.empty();
	}else{
		//删除more
		ul.find('.more').remove();
	}
	for(var i=0;i<notebooks.length;i++){
		var notebook = notebooks[i];
		var li=notebookTemplate.replace('[name]',notebook.name);
		li = $(li);
		//将notebook.id绑定到li
		li.data("notebookId",notebook.id);
		li.data("state",0);//正常的li
		ul.append(li);
	}
	if(notebooks.length!=0){
		var li = moreTemplate;
		li=$(li);
		li.data("state",1);//下一页的li
		ul.append(moreTemplate);
	}
	
}
var moreTemplate="<li class='more' style='text-align: center'>more</li>"
/** 监听回收站按钮被点击*/
function showTrashBin() {
	$('#trash-bin').show();
    $('#notelist').hide();
	var url = "note/showTrashNote.do";
	var data ={userId:getCookie('userId')};
	$.post(url,data,function (result) {
		if(result.stata==SUCCESS){
			var notes = result.data;
			var ul = $('#trash-bin ul');
			ul.empty();
			for(var i=0;i<notes.length;i++){
				var note = notes[i];
				var title = note.title;
				var noteId = note.noteId;
                var li = trashTemplate.replace('[title]',title);
				li = $(li);
				li.data("noteId",noteId);
				ul.append(li);
			}
		}else{
			alert(result.message);
		}
    });

}
function moveNote() {
	var notebookId=$('#moveSelect').val();
	var url="note/upNotebook.do";
	var data={"notebookId":notebookId,"noteId":$(document).data("noteId")};
	$.post(url,data,function(result){
		if(result.stata==0){
			$('#can').empty();
			$('.opacity_bg').hide();

		}else{
			alert(result.message);
		}
	});
}
function addNotebookId() {
	
}
function hideMoveNote() {
	$('#can').empty();
	$('.opacity_bg').hide();
}
function showMoveNote() {
	$('#can').load('alert/alert_move.html',function(){
		$('.opacity_bg').show();
		var url="notebook/list.do";
		var data = {userId:getCookie('userId')};
		$.get(url,data,function(result){
			if(result.stata==SUCCESS){
				var notes=result.data;
				showNoteList(notes);
			}else{
				alert(result.message);
			}
		})
	});
}
//展示笔记
function showNoteList(notes) {
	var sel=$('#moveSelect')
	for(i=0;i<notes.length;i++){
		var note = notes[i];
		var opt='<option value='+note.id+' calss="sel-opt">'+note.name+'</option>';
		opt=$(opt);
		sel.append(opt);
		
	}
}
function hideNoteMenu() {
	$('.note_menu').hide();
}
function showNoteMenu() {
	var btn=$(this);
	//$('.note_menu').hide(function () {
		//找到菜单对象，调用show（）方法
	//如果当前是被选定的笔记项目就弹出子菜单
		btn.parent('.checked').next().toggle();
	/**
	 * btn.parent('.checked')获取当前按钮的父元素
	 * 这个元素必须符合选择器‘.checked’，如果不符合就返回空的JQuery
	 * 元素
	 */
	//});
	
	return false;//阻止点击事件的继续传播
}

function addNote() {
	var url = "note/addNote.do";
	var title=$('#input_note').val();
	if(!title){
		alert("标题不能为空");
		return;
	}
    var notebookId=$(document).data("notebookId");
	var userId=getCookie('userId');
	var data={"notebookId":notebookId,"title":title,"userId":userId};
	$.post(url,data,function(result){
		if(result.stata==SUCCESS){
			closeDialog();
			//还需要补充一个方法，实现关闭后刷新笔记列表
		}else{
			alert("保存笔记失败");
		}
	});
	
}

function closeNoteBookDialog() {
    $('.opacity_bg').hide();
    $('#bookcan').empty();
}
function closeDialog() {
	$('.opacity_bg').hide();
	$('#can').empty();
}
function showAddNoteDialog() {
    var notebookId=$(document).data("notebookId");
    if(!notebookId){
        console.log("错误1");
        alert("请选择笔记本");
        return;
    }
	$('#can').load('alert/alert_note.html',function(){
		$('.opacity_bg').show();
	});
}
function showTitles() {
	var li = $(this);
	li.parent().find('a').removeClass('checked');
	li.find('a').addClass('checked');
	var url="note/select.do";
	var data={"noteId":li.data('noteId')};
	$(document).data("noteId",li.data('noteId'));
	$.getJSON(url,data,function(result){
		if(result.stata==0){
			var notes=result.data;
			showTitle(notes);
			
		}else{
			alert(result.message);
		}
	});
	
}
function showTitle(notes) {
	for(i=0;i<notes.length;i++){
		var note=notes[i];
		var b = note.body;
		var t = note.title;
		$('#input_note_title').val(t);
        um.setContent(b);
	};
	
}
function loadNotebooks() {
	//利用ajax从服务器获取(get)数据
	var url="notebook/list.do";
	var data = {userId:getCookie('userId')};
	$.getJSON(url,data,function(result){
		if(result.stata==SUCCESS){
			var notebooks = result.data;
			//在showNotebooks方法中将全部的笔记本数据
			//notebooks显示到notebook-list区域
			showNotebooks(notebooks);
		}else{
			alert(result.message);
		}
	});
}
/*在notebook-list区域中显示笔记本列表*/
function showNotebooks(notebooks) {
	//找显示笔记本列表的区域
	//遍历notebooks数组，将为每个对象创建一个li元素，添加到ul区域
	var ul = $('#notebook-list ul');
	ul.empty();
	for(i=0;i<notebooks.length;i++){
		var notebook = notebooks[i];
		var li=notebookTemplate.replace('[name]',notebook.name);
		li = $(li);
		//将notebook.id绑定到li
		li.data("notebookId",notebook.id);
		ul.append(li);
	}
};
///笔记本项目点击时间处理方法，加载全部笔记
function showNotes() {
	//关闭回收站，打开笔记列表
	$('#trash-bin').hide();
	$('#notelist').show();
	//console.log(132);
	var li = $(this);//当前被点击的对象
	//在被点击的笔记本增加选定效果
	li.parent().find('a').removeClass('checked');
	li.find('a').addClass('checked');
	var url="notebook/note.do";
	var data = {"notebookId":li.data('notebookId')};
	$(document).data("notebookId",li.data('notebookId'));
	var state = li.data("state");
	if(state==0){
        $.getJSON(url,data,function(result){
            if(result.stata==SUCCESS){
                var notes=result.data;
                showNote(notes);
            }else{
                alert(result.message);
            }
        });
	}
}
function showNote(notes) {
	
	var ul=$('#notelist ul');
	ul.empty();
	//将笔记显示出来
	for(i=0;i<notes.length;i++){
		var note = notes[i];
		var li = noteTemplate.replace('[name]',note.title);
		li=$(li);
		li.data("noteId",note.noteId);
		ul.append(li);
	}
}
//字体图标
var notebookTemplate='<li class="online">'+
					 '<a><i class="fa fa-book" title="online" rel="tooltip-bottom">'+
					 '</i>[name]</a></li>';
var noteTemplate='<li class="online">'+
				 '<a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>[name]<button type="button" class="btn btn-default btn-xs btn_position btn_slide_down btn-note-menu"><i class="fa fa-chevron-down"></i></button></a>'+
				 '<div class="note_menu" tabindex="-1">'+
				 '<dl>'+
				 	'<dt><button type="button" class="btn btn-default btn-xs btn_move" title="移动至..."><i class="fa fa-random"></i></button></dt>'+
					'<dt><button type="button" class="btn btn-default btn-xs btn_share" title="分享"><i class="fa fa-sitemap"></i></button></dt>'+
					'<dt><button type="button" class="btn btn-default btn-xs btn_delete" title="删除"><i class="fa fa-times"></i></button></dt>'+
				'</dl>'+
				'</div>'+
				'</li>';
var trashTemplate ='<li class="disable"><a ><i class="fa fa-file-text-o" title="online" rel="tooltip-bottom"></i>[title]<button type="button" class="btn btn-default btn-xs btn_position btn_delete"><i class="fa fa-times"></i></button><button type="button" class="btn btn-default btn-xs btn_position_2 btn_replay"><i class="fa fa-reply"></i></button></a></li>'
