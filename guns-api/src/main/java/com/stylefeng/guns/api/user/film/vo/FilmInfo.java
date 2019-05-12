package com.stylefeng.guns.api.user.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/4 15:41
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/4 15:41
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
@Data
public class FilmInfo implements Serializable {
    private String filmId;
    private int filmType;
    private String imgAddress;
    private String filmName;
    private String filmScore;
    private int expectNum;
    private String showTime;
    private int boxNum;
    private String sore;

}
