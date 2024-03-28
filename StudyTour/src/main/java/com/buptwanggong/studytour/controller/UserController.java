package com.buptwanggong.studytour.controller;

import com.buptwanggong.studytour.DO.DAO.User;
import com.buptwanggong.studytour.DO.DTO.UserDTO;
import com.buptwanggong.studytour.common.convention.exception.ClientException;
import com.buptwanggong.studytour.common.convention.result.Result;
import com.buptwanggong.studytour.common.convention.result.Results;
import com.buptwanggong.studytour.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关功能接口
 */
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<UserDTO> login(@RequestBody User requestParam) {
        if(requestParam.getUserName()==null||requestParam.getUserPassword()==null){
            throw new ClientException("登录失败，用户名或密码未输入");
        }
        UserDTO login = userService.login(requestParam.getUserName(),requestParam.getUserPassword());
        return Results.success(login);
    }
}
