package com.stylefeng.guns.api.user.film.vo;

import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.io.Serializable;
import java.io.SerializablePermission;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/4 15:22
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/4 15:22
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
@SuppressWarnings("deprecation")
@Data
public class BannerVo implements Serializable{
    private String bannerId;
    private String bannerAddress;
    private String bannerUrl;

}
