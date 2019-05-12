package com.stylefeng.guns.api.user.film.vo.service;

import com.stylefeng.guns.api.user.film.vo.*;

import java.util.List;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/4 16:25
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/4 16:25
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
public interface FilmService {
    // 获取banners
    List<BannerVo> getBanners();
    // 获取热映影片
    HotFilmVo getHotFilmVos(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    // 获取即将上映影片[受欢迎程度做排序]
    HotFilmVo getSoonFilmVo(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);
    // 获取经典影片
    HotFilmVo getClassicFilms(int nums,int nowPage,int sortId,int sourceId,int yearId,int catId);

    // 获取票房排行榜
    List<FilmInfo> getBoxRanking();
    // 获取人气排行榜
    List<FilmInfo> getExpectRanking();
    // 获取Top100排行榜
    List<FilmInfo> getTop100();
    //分类
    List<CatVo> getCats();
    //资源
    List<SourceVO> getSources();
    //年代
    List<YearVO> getYears();

    // 根据影片ID或者名称获取影片信息
    FilmDetailVO getFilmDetail(int searchType,String searchParam);



}
