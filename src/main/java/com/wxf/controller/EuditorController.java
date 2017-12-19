package com.wxf.controller;

import com.baidu.ueditor.MyActionEnter;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by TYZ027 on 2017/11/30.
 */
@Controller
public class EuditorController extends AbstractController{
    @Value("${config.json.path}")
    private String configJSONPath;

    @RequestMapping("/controller")
    @ResponseBody
    public String controller(HttpServletRequest request, HttpServletResponse response) throws IOException, JSONException {
        request.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html");
        @SuppressWarnings("resource")
        ApplicationContext appContext = new ClassPathXmlApplicationContext();
        String baseState = new MyActionEnter(request, appContext.getResource(configJSONPath).getInputStream()).exec();
        // response.getWriter().write(baseState);
        return baseState;
    }

    @RequestMapping("index")
    public String indexDemo(Model model){
        return "index";
    }

    @RequestMapping(value = "/upload/image", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> save(HttpServletRequest req){
        Map<String,Object> rs = new HashMap<String, Object>();
        MultipartHttpServletRequest mReq  =  null;
        MultipartFile file = null;
        // 原始文件名   UEDITOR创建页面元素时的alt和title属性
        String originalFileName = "";
        try {
            mReq = (MultipartHttpServletRequest)req;
            // 从config.json中取得上传文件的ID
            file = mReq.getFile("upfile");

            if(!file.isEmpty()){
                // 取得文件的原始文件名称
                String fileName = file.getOriginalFilename();
                // 获取文件的后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                String prifix=req.getSession().getServletContext().getRealPath("/");
                prifix = prifix.substring(0,prifix.length()-7);
                // 文件上传后的路径
                String filePath =prifix +"resources/static/upload/";
                /*String time =String.valueOf(System.currentTimeMillis());
                fileName = DigestUtils.md5Hex(fileName+time) + suffixName;*/
                File dest = new File(filePath + fileName);
                log.info(dest.getAbsolutePath());
                // 检测是否存在目录
                if (!dest.getParentFile().exists()) {
                    dest.getParentFile().mkdirs();
                }
                try {
                    file.transferTo(dest);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String doMain = "";
                String httpImgPath = doMain  + fileName;

                rs.put("state", "SUCCESS");// UEDITOR的规则:不为SUCCESS则显示state的内容
                rs.put("url",httpImgPath);         //能访问到你现在图片的路径
                rs.put("title", originalFileName);
                rs.put("original", originalFileName);
            }


        } catch (Exception e) {
            e.printStackTrace();
            rs.put("state", "文件上传失败!"); //在此处写上错误提示信息，这样当错误的时候就会显示此信息
            rs.put("url","");
            rs.put("title", "");
            rs.put("original", "");
        }
        return rs;
    }


}
