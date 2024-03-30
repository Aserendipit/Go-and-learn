package com.buptwanggong.studytour.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buptwanggong.studytour.DO.DAO.MyMap;
import com.buptwanggong.studytour.DO.DTO.MyMapOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MyMapMapper extends BaseMapper<MyMap> {
    @Select("SELECT m.*, COUNT(r.rating) AS population, AVG(r.rating) AS score " +
            "FROM map m " +
            "LEFT JOIN userreviews r ON m.map_id = r.map_id " +
            "GROUP BY m.map_id")
    List<MyMapOrder> selectMapsWithStatistics();
}
