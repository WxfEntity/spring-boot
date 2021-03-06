package com.wxf.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TYZ027 on 2017/7/31.
 */
public class ClientTest {
    public static List<User> initUserList(){

        User user1=new User();
        user1.setUserName("张三");
        user1.setUserPassword("123");
        user1.setAge(20);

        User user2=new User();
        user2.setUserName("李四");
        user2.setUserPassword("123");
        user2.setAge(22);

        User user3=new User();
        user3.setUserName("王五");
        user3.setUserPassword("123");
        user3.setAge(21);
        List<User> list=new ArrayList<User>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        return list;
    }
    public static void main(String[] args){
        List<User> list=ClientTest.initUserList();
        Map<String,Object> root=new HashMap<String,Object>();
        root.put("userList",list);
        FreeMarkertUtil.analysisTemplate("user.ftl","UTF-8",root);
        //FreeMarkUtil类可以参考我上传得源码。
    }
}
