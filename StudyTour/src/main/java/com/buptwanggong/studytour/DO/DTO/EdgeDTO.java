package com.buptwanggong.studytour.DO.DTO;

import com.buptwanggong.studytour.DO.DAO.Point;
import lombok.Data;

@Data
public class EdgeDTO {
    private String destination;
    private double distance;

    public EdgeDTO() {
    }

    public EdgeDTO(PointDTO destination, double distance) {
        this.destination=destination.getPointId();
        this.distance = distance;
    }
}

