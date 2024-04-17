package com.buptwanggong.studytour.controller;

import com.buptwanggong.studytour.DO.DAO.MyMap;
import com.buptwanggong.studytour.DO.DAO.User;
import com.buptwanggong.studytour.DO.DTO.Graph;
import com.buptwanggong.studytour.DO.DTO.MyMapOrder;
import com.buptwanggong.studytour.DO.DTO.SearchDTO;
import com.buptwanggong.studytour.DO.DTO.UserDTO;
import com.buptwanggong.studytour.common.convention.exception.ClientException;
import com.buptwanggong.studytour.common.convention.result.Result;
import com.buptwanggong.studytour.common.convention.result.Results;
import com.buptwanggong.studytour.service.MapService;
import com.buptwanggong.studytour.service.impl.MapServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.jdbc.Null;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;
    @GetMapping("/maptest")
    public Result<Graph> maptest()
    {
        Graph graph=mapService.getGraph(8);
        return Results.success(graph);
    }

    @PostMapping("/search")
    public Result<List<MyMapOrder>> search(@RequestBody SearchDTO requestParam) {

        List<MyMapOrder> searchResult = mapService.getSearchResult(requestParam);
        return Results.success(searchResult);
    }

}
