package com.buptwanggong.studytour.DO.DTO;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class Graph {
    private Map<String, PointDTO> points; // 使用点的ID作为键
    public Graph() {
        this.points = new HashMap<>();
    }

    public void addPoint(PointDTO point) {
        points.put(point.getPointId(), point);
    }

    public void addEdge(String sourceId, String destinationId, double distance) {
        PointDTO source = points.get(sourceId);
        PointDTO destination = points.get(destinationId);
        if (source != null && destination != null) {
            // 为源点添加指向目标点的边
            EdgeDTO edgeDTOToDestination = new EdgeDTO(destination, distance);
            source.getNeighbors().add(edgeDTOToDestination);

            // 由于是无向图，也需要为目标点添加指向源点的边
            EdgeDTO edgeDTOToSource = new EdgeDTO(source, distance);
            destination.getNeighbors().add(edgeDTOToSource);
        }
    }
}
