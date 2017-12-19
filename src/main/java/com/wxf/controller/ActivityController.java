package com.wxf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by TYZ027 on 2017/9/12.
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController{

    @RequestMapping("helloWord.do")
    public String helloWord(Model model){
        String word0 = "Hello ";
        String word1 = "World!";
        //將數據添加到視圖數據容器中
        model.addAttribute("word0",word0);
        model.addAttribute("word1",word1);
        return "hello.ftl";
    }
}
