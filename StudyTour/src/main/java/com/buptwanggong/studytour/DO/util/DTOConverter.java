package com.buptwanggong.studytour.DO.util;

import com.buptwanggong.studytour.DO.DAO.Edge;
import com.buptwanggong.studytour.DO.DAO.Point;
import com.buptwanggong.studytour.DO.DTO.EdgeDTO;
import com.buptwanggong.studytour.DO.DTO.PointDTO;

import java.util.Map;

public class DTOConverter {
    public static PointDTO convertToPointDTO(Point point) {
        PointDTO dto = new PointDTO();
        dto.setPointId(point.getPointId());
        dto.setMapId(point.getMapId());
        dto.setX(point.getX());
        dto.setY(point.getY());
        dto.setType(point.getType());
        dto.setWidth(point.getWidth());
        dto.setLength(point.getLength());
        return dto;
    }

    public static EdgeDTO convertToEdgeDTO(Edge edge, Map<String, PointDTO> pointsMap) {
        EdgeDTO dto = new EdgeDTO();
        dto.setDestination(edge.getDestinationPointId());
        dto.setDistance(edge.getDistance());
        return dto;
    }
}
