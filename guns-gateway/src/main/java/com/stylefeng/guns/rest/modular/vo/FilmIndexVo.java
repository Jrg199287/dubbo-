package com.stylefeng.guns.rest.modular.vo;

import com.stylefeng.guns.api.user.film.vo.BannerVo;
import com.stylefeng.guns.api.user.film.vo.FilmInfo;
import com.stylefeng.guns.api.user.film.vo.HotFilmVo;
import lombok.Data;

import java.util.List;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/4 15:20
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/4 15:20
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
@Data
public class FilmIndexVo {
    private List<BannerVo> banners;
    private HotFilmVo hotFilmVo;
    private HotFilmVo soonFilmVo;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRanking ;
    private List<FilmInfo> top100;

}
