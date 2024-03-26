package com.buptwanggong.studytour.DO.DAO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 用户实体类
 */
@Data
@TableName("`user`")
public class User {
    private String UserId;
    private String UserName;
    private String UserPassword;
}
