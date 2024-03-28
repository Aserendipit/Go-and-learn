package com.buptwanggong.studytour.service;

import com.buptwanggong.studytour.DO.DAO.MyMap;
import com.buptwanggong.studytour.DO.DTO.Graph;

public interface MapService {
    public MyMap getMap(Integer mapId);
    public Graph getGraph(Integer mapId);
}
