package com.stylefeng.guns.api.user.film.vo;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/4 15:40
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/4 15:40
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
@Data
public class HotFilmVo implements Serializable {
    private int filmNum;
    private int nowPage;
    private int totalPage;
    private List<FilmInfo> filmInfo;


}
