package com.buptwanggong.studytour.DO.DTO;

import lombok.Data;

@Data
public class SearchDTO {
    String searchType;//类型
    String searchContent;//名称或关键字
    String orderType;//热度或评价
}
