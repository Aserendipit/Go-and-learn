package com.buptwanggong.studytour.controller;

import com.buptwanggong.studytour.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 */
@RestController
public class helloController
{
    @GetMapping("/hello")
    public String hello()
    {
        return "HELLO";
    }
}