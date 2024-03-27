package com.buptwanggong.studytour.controller;

import com.buptwanggong.studytour.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class helloController
{

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public String hello()
    {

        return "HELLO";
    }



}