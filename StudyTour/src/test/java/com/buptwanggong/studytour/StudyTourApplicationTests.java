package com.buptwanggong.studytour;

import com.alibaba.fastjson2.JSON;
import com.buptwanggong.studytour.DO.DAO.Edge;
import com.buptwanggong.studytour.DO.DAO.MyMap;
import com.buptwanggong.studytour.DO.DAO.Point;
import com.buptwanggong.studytour.DO.DTO.Graph;
import com.buptwanggong.studytour.DO.DTO.PointDTO;
import com.buptwanggong.studytour.mapper.EdgeMapper;
import com.buptwanggong.studytour.mapper.MyMapMapper;
import com.buptwanggong.studytour.mapper.PointMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class StudyTourApplicationTests {
    @Autowired
    private MyMapMapper mapMapper;
    @Autowired
    private PointMapper pointMapper;
    @Autowired
    private EdgeMapper edgeMapper;
//    @Test
//    void readjson(){
//        try {
//            // 使用ClassLoader获取文件路径
//            ClassLoader classLoader = getClass().getClassLoader();
//            File file = new File(classLoader.getResource("graphData-2024328-195759.json").getFile());
//
//            // 读取JSON文件内容为String
//            String content = new String(Files.readAllBytes(file.toPath()));
//            Graph graph = JSON.parseObject(content, Graph.class);
//            // 创建并保存MapEntity
//            MyMap mapEntity = new MyMap();
//            mapEntity.setMapName("华国邮电大学");
//            mapEntity.setDescription("华国邮电大学是华国重点大学");
//            int insert = mapMapper.insert(mapEntity);
//            // 获取map_id
//            String mapId = String.valueOf(mapEntity.getMapId());
//            System.out.println(mapId);
//            Map<String, String> pointIdMap = new HashMap<>(); // 存储pointId到数据库ID的映射
//
//            // 对于Graph中的每个点，创建并保存PointEntity
//            graph.getPoints().forEach((id, pointDTO) -> {
//                Point pointEntity = new Point();
//                BeanUtils.copyProperties(pointDTO,pointEntity);
//                pointEntity.setMapId(mapId);
//                pointEntity.setPointId(null);
//                pointMapper.insert(pointEntity);
//                // 保存pointId到数据库ID的映射
//                pointIdMap.put(id, pointEntity.getPointId());
//            });
//            // 插入所有边
//            graph.getPoints().forEach((id, pointDTO) -> {
//                pointDTO.getNeighbors().forEach(neighbor -> {
//                    Edge edgeEntity = new Edge();
//                    edgeEntity.setSourcePointId(pointIdMap.get(id)); // 使用映射找到源点的数据库ID
//                    edgeEntity.setDestinationPointId(pointIdMap.get(neighbor.getDestination())); // 使用映射找到目标点的数据库ID
//                    edgeEntity.setDistance(neighbor.getDistance());
//                    edgeEntity.setMapId(mapId);
//                    edgeMapper.insert(edgeEntity);
//                });
//            });
//            System.out.println("success");

//        } catch (IOException | NullPointerException e) {
//            e.printStackTrace();
//        }
//    }

}
