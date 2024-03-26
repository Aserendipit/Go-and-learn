package com.example.springbootdemo.DO.DAO;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("`user`")
public class User {
    private String UserId;
    private String UserName;
    private String UserPassword;
//springmvc
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }
}
