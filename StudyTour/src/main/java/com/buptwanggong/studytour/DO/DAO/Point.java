package com.buptwanggong.studytour.DO.DAO;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 表示建筑物的节点实体类
 */
@Data
@TableName("`points`")
public class Point {
    @TableId(type = IdType.AUTO)
    private String pointId; // 点的唯一标识符
    private String mapId;   // 表示点所属的地图的ID
    private double x;          // 点的x坐标
    private double y;          // 点的y坐标
    private String type;    // 点的类型  "crossing"表示路口,其余是建筑物，包括主要建筑物和次要建筑物，如果是景区则有attractions类别
    /**
     *  mainBuildingTypes = ["attractions","teachingBuildings", "officeBuildings", "dormitoryBuildings"];
     *   secondaryBuildingTypes = [
     *   "shops", "restaurants", "restrooms", "libraries",
     *   "canteens", "supermarkets", "cafes", "parkingLots",
     *   "restAreas", "gymnasiums", "sportsFields", "lectureHalls",
     *   "landscapingAreas", "landmarks"
     * ];
     */
    private int width;      // 如果点代表一个区域，这是它的宽度
    private int length;     // 如果点代表一个区域，这是它的长度
}
