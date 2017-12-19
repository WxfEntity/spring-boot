package com.wxf.utils;

/**
 * Created by TYZ027 on 2017/7/31.
 */
public class User {


    private String userName ;

    private int age;

    private String userPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public User(String userName, int age, String userPassword) {
        this.userName = userName;
        this.age = age;
        this.userPassword = userPassword;
    }
    public User(){};
}
