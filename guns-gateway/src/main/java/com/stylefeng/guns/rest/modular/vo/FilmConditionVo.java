package com.stylefeng.guns.rest.modular.vo;

import com.stylefeng.guns.api.user.film.vo.*;
import lombok.Data;

import java.util.List;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/10 22:07
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/10 22:07
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
@Data
public class FilmConditionVo {
    private List<CatVo> catVos;
    private List<SourceVO> sourceVOS;
    private List<YearVO> yearVOS ;
}
