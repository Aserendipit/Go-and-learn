package com.buptwanggong.studytour.service.impl;


import com.buptwanggong.studytour.DO.DAO.User;
import com.buptwanggong.studytour.DO.DTO.UserDTO;
import com.buptwanggong.studytour.common.convention.exception.ClientException;
import com.buptwanggong.studytour.mapper.UserMapper;
import com.buptwanggong.studytour.service.CacheService;
import com.buptwanggong.studytour.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static com.buptwanggong.studytour.common.convention.constant.CacheConstant.USER_LOGIN_KEY;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final CacheService cacheService;
    private final UserMapper userMapper;
    @Override
    public UserDTO login(String username, String password) {
        //1.检查缓存是否有登录信息
        UserDTO userDTO;
        userDTO=(UserDTO) cacheService.get(USER_LOGIN_KEY +username);
        if (userDTO!= null) {
            if(userDTO.getUserPassword()==password){
                return (UserDTO) cacheService.get(USER_LOGIN_KEY +username);
            }
            else{
                throw new ClientException("登录失败，用户名不存在或密码错误");
            }
        }
        //2.验证数据库是否存在
        List<User> userList = userMapper.selectList(null);
        if (userList == null || userList.isEmpty()) {
            throw new ClientException("登录失败，用户名不存在或密码错误");
        }

        User loginUser = null;
        for (User user : userList) {
            if (username.equals(user.getUserName()) && password.equals(user.getUserPassword())) {
                loginUser = user; // 找到匹配的用户
                break;
            }
        }
        if (loginUser == null) {
            throw new ClientException("登录失败，用户名不存在或密码错误");
        }
        //3.将登录信息tonken存到缓存中
        String uuid = UUID.randomUUID().toString();
        userDTO= new UserDTO(username, password,uuid);
        cacheService.set(USER_LOGIN_KEY + username, userDTO);
        userDTO.setUserPassword(null);
        return userDTO;
    }
}
