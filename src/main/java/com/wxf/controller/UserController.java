package com.wxf.controller;


import com.wxf.domain.User;
import com.wxf.service.PasswordException;
import com.wxf.service.UserNameException;
import com.wxf.service.UserNotFoundException;
import com.wxf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.*;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController{
	@Autowired
	private UserService userService;
	
	@RequestMapping("/login.do")
	@ResponseBody
	public Object login(String name,String password,HttpSession session){
			User user = userService.login(name, password);
			//登录成功时，将user信息保存到session。用于在过滤器中检查
			//登录情况
			session.setAttribute("loginUser", user);

			return new JsonResult(user);
			
	}
	@RequestMapping("/exit.do")
	@ResponseBody
	public void exit(HttpSession session, ServletResponse response) throws IOException {
		log.error(session.getAttribute("loginUser").toString());
		session.removeAttribute("loginUser");
		HttpServletResponse res=(HttpServletResponse)response;
		res.sendRedirect("../log_in.html");
	}
	@RequestMapping("/checkUser.do")
	@ResponseBody
	public JsonResult checkUser(HttpSession session){
		User user=(User) session.getAttribute("loginUser");
		if(user==null){
			return new JsonResult("未登录");
		}else{
			return new JsonResult(1);
		}
	}

	@RequestMapping("/loadFtl.do")
	public String loadFtl(Model model){

		model.addAttribute("word0","hello");
		model.addAttribute("word1","ftl");
		return "myblog";
	}
	//处理登录帐号错误的异常
	@ExceptionHandler(UserNotFoundException.class)
	@ResponseBody
	public JsonResult handleUserNotFound(UserNotFoundException e){
		e.printStackTrace();
		return new JsonResult(2,e);
	}
	//处理登录密码错误的异常
	@ExceptionHandler(PasswordException.class)
	@ResponseBody
	public JsonResult handlePassword(PasswordException e){
		e.printStackTrace();
		return new JsonResult(3,e);
	}
	//处理注册帐号错误的异常
	@ExceptionHandler(UserNameException.class)
	@ResponseBody
	public JsonResult handleUserException(UserNameException e){
		e.printStackTrace();
		return new JsonResult(4,e);
	}
	//其他控制器也有这个异常，所有把它写到父类里取
	//处理其他异常
//	@ExceptionHandler(Exception.class)
//	@ResponseBody
//	public Object handException(Exception e){
//		e.printStackTrace();
//		return new JsonResult(e);
//	}
	@RequestMapping("/regist.do")
	@ResponseBody
	public JsonResult regist(String name,String nick,String password,String confirm){
		User user = userService.regist(name, nick, password, confirm);
		return new JsonResult(user);
		
	}
	/**
	 * 创建一张图片，编码为png格式，返回编码以后的数据
	 * @return
	 * @throws IOException 
	 */
	public  byte[] createPng() throws IOException{
		BufferedImage img = new BufferedImage(200, 80, BufferedImage.TYPE_3BYTE_BGR);
		//在图片上绘制内容
		img.setRGB(100, 40, 0xffffff);
		
		//将图片编码为png
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(img, "png", out);
		byte[] png = out.toByteArray();
		out.close();		
		return png;
	}
	/**
	 * @ResponseBody 注解会自动处理控制返回值
	 * 1.如果是javaBean（数组，集合）返回json
	 * 2.如果是byte数组，则将byte数组直接装入响应消息的body
	 * 
	 * @return
	 * @throws IOException
	 */
	//produces="image/png"相当于设置con
	@RequestMapping(value="/image.do",produces="image/png")
	@ResponseBody
	public byte[] image() throws IOException{
		return createPng();
	}
	@RequestMapping(value="excel.do",produces="application/octet-stream")
	@ResponseBody
	public byte[] downloading(HttpServletResponse res) {
		res.setHeader("Content-Disposition", "attachment;filename=\'demo.xlsx'");
		return null;
		
	}
	
	/*private byte[] createExcel() throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("demo");
		//在工作表中创建数据行
		HSSFRow row = sheet.createRow(0);
		HSSFCell cell = row.createCell(0);
		cell.setCellValue("hello world!");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		workbook.write(out);
		out.close();
		
		return out.toByteArray();
		
	}*/

	@RequestMapping("/changePwd.do")
	@ResponseBody
	public JsonResult checkPwd(String userId,String password,String newPassword,String finalPassword){
		userService.changePwd(userId,password,newPassword,finalPassword);
		return new JsonResult(0);
	}

	/**
	 * 接受上传的文件
	 * @param userfile1
	 * @param userfile2
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload.do")
	@ResponseBody
	public JsonResult upload( 
			MultipartFile userfile1,
			MultipartFile userfile2) throws Exception{
		//Spring MVC 中可以利用 MultipartFile 
		//接收 上载的文件! 文件中的一切数据
		//都可以从 MultipartFile 对象中找到
		
		//获取上再是原始文件名
		String file1 = 
			userfile1.getOriginalFilename();
		String file2 = 
			userfile2.getOriginalFilename();
		
		System.out.println(file1);
		System.out.println(file2);
		
		//保存文件的3种方法:
		//1. transferTo(目标文件)
		//   将文件直接保存到目标文件, 可以处理大文件
		//2. userfile1.getBytes() 获取文件的全部数据
		//   将文件全部读取到内存, 适合处理小文件!!
		//3. userfile1.getInputStream()
		//   获取上载文件的流, 适合处理大文件
		
		//保存的目标文件夹: /home/soft01/demo
		File dir = new File("/home/soft01/tts9/workspace/webbasic/doc/images");
		dir.mkdir();
		
		File f1 = new File(dir, file1);
		File f2 = new File(dir, file2);
		
		//第一种保存文件
		//userfile1.transferTo(f1);
		//userfile2.transferTo(f2);
		
		//第三种 利用流复制数据
		InputStream in1 = userfile1.getInputStream();
		FileOutputStream out1 = 
			new FileOutputStream(f1);
		int b;
		while((b=in1.read())!=-1){
			out1.write(b);
		}
		in1.close();
		out1.close();
		
		InputStream in2 = userfile2.getInputStream();
		FileOutputStream out2=
				new FileOutputStream(f2);
		byte[] buf= new byte[8*1024];
		int n;
		while((n=in2.read(buf))!=-1){
			out2.write(buf, 0, n);
		}
		in2.close();
		out2.close();
		
		return new JsonResult(true);
	}
}
