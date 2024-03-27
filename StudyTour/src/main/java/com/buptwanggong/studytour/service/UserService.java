package com.buptwanggong.studytour.service;

import com.buptwanggong.studytour.DO.DAO.User;
import com.buptwanggong.studytour.DO.DTO.UserDTO;


public interface UserService {
    public UserDTO login(String username , String password);
}
