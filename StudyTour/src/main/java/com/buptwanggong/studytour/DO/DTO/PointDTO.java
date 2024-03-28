package com.buptwanggong.studytour.DO.DTO;

import com.buptwanggong.studytour.DO.DAO.Point;
import lombok.Data;

import java.util.List;

@Data
public class PointDTO extends Point {
    private List<EdgeDTO> neighbors; // 与此点相连的边的列表
}
