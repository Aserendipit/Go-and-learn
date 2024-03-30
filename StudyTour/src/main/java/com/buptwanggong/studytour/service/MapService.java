package com.buptwanggong.studytour.service;

import com.buptwanggong.studytour.DO.DAO.MyMap;
import com.buptwanggong.studytour.DO.DTO.Graph;
import com.buptwanggong.studytour.DO.DTO.MyMapOrder;
import com.buptwanggong.studytour.DO.DTO.SearchDTO;

import java.util.List;

public interface MapService {
    public MyMap getMap(Integer mapId);
    public Graph getGraph(Integer mapId);
    public List<MyMapOrder> getSearchResult(SearchDTO searchDTO);
}
