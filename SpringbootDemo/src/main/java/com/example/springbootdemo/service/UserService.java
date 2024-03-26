package com.example.springbootdemo.service;

import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public String login(String username ,String password);
}
