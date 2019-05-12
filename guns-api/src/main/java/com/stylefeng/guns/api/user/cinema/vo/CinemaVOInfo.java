package com.stylefeng.guns.api.user.cinema.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CinemaVOInfo implements Serializable {

    private List<CinemaVO> records;
    private Integer size;
    private Long total;
    private Integer current;


}
