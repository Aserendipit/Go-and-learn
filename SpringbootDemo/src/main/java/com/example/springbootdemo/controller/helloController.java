package com.example.springbootdemo.controller;

import com.example.springbootdemo.DO.DAO.User;
import com.example.springbootdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@RestController
//Representational State Transfer
public class helloController
{
//    @Autowired
//    private DataSource dataSource;
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public String hello()
    {

        List<User> userList = userMapper.selectList(null);
//        System.out.println("DataSource="+dataSource);

        return "HELLO小猪";
    }



}