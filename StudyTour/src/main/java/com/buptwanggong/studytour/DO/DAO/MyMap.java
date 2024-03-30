package com.buptwanggong.studytour.DO.DAO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`map`")
public class MyMap {
    @TableId(type = IdType.AUTO)
    private String mapId;
    private String mapName;
    private String description;
    private String mapType;
    private String countryRegion;
    private String imageId;

    public MyMap() {
    }

    public MyMap(String mapName, String description) {
        this.mapName = mapName;
        this.description = description;
    }
}
