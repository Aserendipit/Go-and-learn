package com.buptwanggong.studytour.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buptwanggong.studytour.DO.DAO.Edge;
import com.buptwanggong.studytour.DO.DAO.MyMap;
import com.buptwanggong.studytour.DO.DAO.Point;
import com.buptwanggong.studytour.DO.DTO.*;
import com.buptwanggong.studytour.DO.util.DTOConverter;
import com.buptwanggong.studytour.mapper.EdgeMapper;
import com.buptwanggong.studytour.mapper.MyMapMapper;
import com.buptwanggong.studytour.mapper.PointMapper;
import com.buptwanggong.studytour.service.CacheService;
import com.buptwanggong.studytour.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.buptwanggong.studytour.common.convention.constant.CacheConstant.ALL_MAP_LIST_KEY;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final MyMapMapper myMapMapper;
    private final EdgeMapper edgeMapper;
    private final PointMapper pointMapper;
    private final CacheService cacheService;
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
        List<Edge> edges = edgeMapper.selectList(new QueryWrapper<Edge>().lambda().eq(Edge::getMapId, mapId));
        for (Edge edge : edges) {
            EdgeDTO edgeDTO =new EdgeDTO();
            edgeDTO.setDistance(edge.getDistance());
            edgeDTO.setDestination(edge.getDestinationPointId().toString());
            // 将边添加到对应的起始点
            PointDTO sourceDTO = pointsDTOMap.get(edge.getSourcePointId().toString());
            PointDTO destinationDTO = pointsDTOMap.get(edge.getDestinationPointId().toString());
            if (sourceDTO != null && destinationDTO != null) {
                sourceDTO.getNeighbors().add(edgeDTO);
                edgeDTO.setDestination(sourceDTO.getPointId());
                destinationDTO.getNeighbors().add(edgeDTO); // 由于是无向图
            }
        }
        return graph;
    }

    @Override
    public List<MyMapOrder> getSearchResult(SearchDTO searchDTO) {
//        public class SearchDTO {
//            String searchType;//类型
//            String searchContent;//名称或关键字
//            String orderType;//热度或评价
//        }
        List<MyMapOrder> myMaps = (List<MyMapOrder>)cacheService.get(ALL_MAP_LIST_KEY);
        if(myMaps==null){
            myMaps = myMapMapper.selectMapsWithStatistics();
            cacheService.set(ALL_MAP_LIST_KEY,myMaps);
        }else{
            myMaps = new ArrayList<>(myMaps);
        }
        List<MyMapOrder> myMapSelect=new ArrayList<>();
        Boolean onlyTop10=false;
        if(searchDTO.getSearchType()==null&&searchDTO.getSearchContent()==null)onlyTop10=true;
        else
        {
            // 遍历myMaps，选取符合条件的元素添加到myMapSelect中
            for (MyMapOrder mapOrder : myMaps) {
                boolean matchesSearchType = true; // 默认为true，即当searchDTO的searchType为空时不影响筛选
                boolean matchesSearchContent = true; // 同上

                // 如果searchType不为空，则判断MyMapOrder的searchType是否与之相等
                if (searchDTO.getSearchType() != null && !searchDTO.getSearchType().isEmpty()) {
                    matchesSearchType = searchDTO.getSearchType().equals(mapOrder.getMapType());
                }

                // 如果searchContent不为空，则判断MyMapOrder的mapName或description是否包含searchDTO的searchContent
                if (searchDTO.getSearchContent() != null && !searchDTO.getSearchContent().isEmpty()) {
                    matchesSearchContent = mapOrder.getMapName().contains(searchDTO.getSearchContent())
                            || mapOrder.getDescription().contains(searchDTO.getSearchContent());
                }

                // 如果同时满足上述两个条件，则将mapOrder添加到myMapSelect中
                if (matchesSearchType && matchesSearchContent) {
                    myMapSelect.add(mapOrder);
                }
                myMaps=myMapSelect;
            }
        }
        if(searchDTO.getOrderType()==null)searchDTO.setOrderType("population");
        List<MyMapOrder> myMapOrders =null;
        if(onlyTop10){
            myMapOrders = heapSort(myMaps, searchDTO.getOrderType(), 10);
        }else{
             myMapOrders =heapSort(myMaps,searchDTO.getOrderType(),myMaps.size());
        }

        return myMapOrders;
    }
    public List<MyMapOrder> heapSort(List<MyMapOrder> myMaps,String orderType,int num){
        // Build the heap in array a so that largest value is at the root
        int n = myMaps.size();
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(myMaps, n, i, orderType);
        }

        // Extract the elements from the heap one by one
        // We only sort the first 'num' elements if 'num' is less than the size of the list
        int end = Math.min(num, n);
        for (int i = n - 1; i >= n - end; i--) {
            // Move current root to end, which is considered sorted
            MyMapOrder temp = myMaps.get(0);
            myMaps.set(0, myMaps.get(i));
            myMaps.set(i, temp);

            // call max heapify on the reduced heap
            heapify(myMaps, i, 0, orderType);
        }

        return myMaps.subList(0, end); // Return only the sorted top 'num' elements
    }
    private void heapify(List<MyMapOrder> myMaps, int heapSize, int i, String orderType) {
        int smallest = i; // Initialize smallest as root
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        // If left child is smaller than root
        if (left < heapSize && compare(myMaps.get(left), myMaps.get(smallest), orderType) < 0) {
            smallest = left;
        }

        // If right child is smaller than smallest so far
        if (right < heapSize && compare(myMaps.get(right), myMaps.get(smallest), orderType) < 0) {
            smallest = right;
        }

        // If smallest is not root
        if (smallest != i) {
            MyMapOrder swap = myMaps.get(i);
            myMaps.set(i, myMaps.get(smallest));
            myMaps.set(smallest, swap);

            // Recursively heapify the affected sub-tree
            heapify(myMaps, heapSize, smallest, orderType);
        }
    }

    private int compare(MyMapOrder a, MyMapOrder b, String orderType) {
        if ("score".equals(orderType)) {
            return Double.compare(a.getScore(), b.getScore());
        } else { // Default to "population"
            return Integer.compare(a.getPopulation(), b.getPopulation());
        }
    }

}