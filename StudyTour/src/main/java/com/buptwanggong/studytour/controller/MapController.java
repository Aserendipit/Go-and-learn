package com.buptwanggong.studytour.controller;

import com.buptwanggong.studytour.DO.DTO.Graph;
import com.buptwanggong.studytour.DO.DTO.UserDTO;
import com.buptwanggong.studytour.common.convention.result.Result;
import com.buptwanggong.studytour.common.convention.result.Results;
import com.buptwanggong.studytour.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;
    @GetMapping("/maptest")
    public Result<Graph> hello()
    {
        Graph graph=mapService.getGraph(8);
        return Results.success(graph);
    }
}
