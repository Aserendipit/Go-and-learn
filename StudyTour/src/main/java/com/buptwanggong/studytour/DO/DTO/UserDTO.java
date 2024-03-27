package com.buptwanggong.studytour.DO.DTO;

import com.buptwanggong.studytour.DO.DAO.User;
import lombok.Data;

@Data
public class UserDTO extends User {
    private String token;

    public UserDTO(String username, String uuid) {
        this.setUserName(username);
        this.setToken(uuid);
    }

    public UserDTO() {

    }
}
