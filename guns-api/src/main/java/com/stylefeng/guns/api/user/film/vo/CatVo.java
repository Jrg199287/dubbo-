package com.stylefeng.guns.api.user.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/10 21:50
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/10 21:50
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
@Data
public class CatVo implements Serializable {
    private String catId;
    private String catName;
    private boolean isActive;
}
