package com.stylefeng.guns.api.user.film.vo;

import lombok.Data;

@Data
public class InfoRequstVO {

    private String biography;
    private ActorRequestVO actors;
    private ImgVO imgVO;
    private String filmId;

}
