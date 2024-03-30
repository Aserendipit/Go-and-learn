package com.buptwanggong.studytour.DO.DTO;

import com.buptwanggong.studytour.DO.DAO.MyMap;
import lombok.Data;

@Data
public class MyMapOrder extends MyMap {
    int population;
    double score;
}
