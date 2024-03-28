package com.buptwanggong.studytour.DO.DAO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("`edge`")
public class Edge {
    @TableId(type = IdType.AUTO)
    private String edgeId ; //唯一标识一条边。
    private String sourcePointId ;//表示边的起点。
    private String destinationPointId;//表示边的终点。
    private double distance; //边的长度。
    private String mapId; //地图id
}
