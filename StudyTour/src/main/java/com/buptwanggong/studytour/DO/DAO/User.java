package com.buptwanggong.studytour.DO.DAO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.buptwanggong.studytour.DO.DTO.Interest;
import lombok.Data;

import java.util.List;

/**
 * 用户实体类
 */
@Data
@TableName("`user`")
public class User {
    private String userId;
    private String userName;
    private String userPassword;
    private String email;
    private String phoneNumber;
    @TableField(exist = false) // 表示 interests 字段在数据库中不存在
    private List<Interest> interests; // 兴趣列表
}
