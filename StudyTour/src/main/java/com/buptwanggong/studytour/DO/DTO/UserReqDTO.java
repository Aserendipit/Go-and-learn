package com.buptwanggong.studytour.DO.DTO;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@Data
public class UserReqDTO {
    private String UserName;
    private String UserPassword;
}

