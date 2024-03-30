package com.buptwanggong.studytour.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.buptwanggong.studytour.DO.DAO.User;
import com.buptwanggong.studytour.DO.DTO.Interest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 新增方法以获取特定用户的兴趣列表
    @Select("SELECT i.interest_id, i.interest_name FROM interests i " +
            "JOIN user_interests ui ON i.interest_id = ui.interest_id " +
            "WHERE ui.user_id = #{userId}")
    List<Interest> selectInterestsByUserId(@Param("userId") int userId);
}