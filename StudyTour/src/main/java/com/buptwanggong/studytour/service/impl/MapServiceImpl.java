package com.buptwanggong.studytour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buptwanggong.studytour.DO.DAO.Edge;
import com.buptwanggong.studytour.DO.DAO.MyMap;
import com.buptwanggong.studytour.DO.DAO.Point;
import com.buptwanggong.studytour.DO.DTO.EdgeDTO;
import com.buptwanggong.studytour.DO.DTO.Graph;
import com.buptwanggong.studytour.DO.DTO.PointDTO;
import com.buptwanggong.studytour.DO.util.DTOConverter;
import com.buptwanggong.studytour.mapper.EdgeMapper;
import com.buptwanggong.studytour.mapper.MyMapMapper;
import com.buptwanggong.studytour.mapper.PointMapper;
import com.buptwanggong.studytour.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final MyMapMapper myMapMapper;
    private final EdgeMapper edgeMapper;
    private final PointMapper pointMapper;
    @Override
    public MyMap getMap(Integer mapId) {
        return myMapMapper.selectById(mapId);
    }

    @Override
    public Graph getGraph(Integer mapId) {
        Graph graph = new Graph();
        // 获取地图的所有点并转换为DTO
        List<Point> points = pointMapper.selectList(new QueryWrapper<Point>().eq("map_id", mapId));
        Map<String, PointDTO> pointsDTOMap = new HashMap<>();
        points.forEach(point -> {
            PointDTO pointDTO = new PointDTO();
            BeanUtils.copyProperties(point, pointDTO);
            pointDTO.setNeighbors(new ArrayList<>());
            pointsDTOMap.put(pointDTO.getPointId().toString(), pointDTO);
            graph.addPoint(pointDTO);
        });
        // 获取并转换边，更新邻接信息
        List<Edge> edges = edgeMapper.selectList(new QueryWrapper<Edge>().lambda()
                .eq(Edge::getMapId, mapId));
        for (Edge edge : edges) {
            EdgeDTO edgeDTO =new EdgeDTO();
            edgeDTO.setDistance(edge.getDistance());
            edgeDTO.setDestination(edge.getDestinationPointId().toString());
            // 将边添加到对应的起始点
            PointDTO sourceDTO = pointsDTOMap.get(edge.getSourcePointId().toString());
            PointDTO destinationDTO = pointsDTOMap.get(edge.getDestinationPointId().toString());
            if (sourceDTO != null && destinationDTO != null) {
                sourceDTO.getNeighbors().add(edgeDTO);
                destinationDTO.getNeighbors().add(edgeDTO); // 由于是无向图
            }
        }
        return graph;
    }
}