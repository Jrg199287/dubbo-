package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.stylefeng.guns.api.user.film.vo.*;
import com.stylefeng.guns.api.user.film.vo.service.FilmService;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/7 20:50
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/7 20:50
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
@Component
@Service
public class DefaultServiceImpl  implements FilmService {
    @Autowired
    private  MoocBannerTMapper moocBannerTMapper;
    @Autowired
    private MoocFilmInfoTMapper moocFilmInfoTMapper;
    @Autowired
    private MoocFilmTMapper  moocFilmTMapper;
    @Autowired
    private MoocCatDictTMapper moocCatDictTMapper;
    @Autowired
    private MoocYearDictTMapper moocYearDictTMapper;
    @Autowired
    private MoocSourceDictTMapper moocSourceDictTMapper;
    @Autowired
    private MoocActorTMapper moocActorTMapper;
    @Override
    public List<BannerVo> getBanners() {
        List<BannerVo> result = new ArrayList<>();
        List<MoocBannerT> moocBannerTS =moocBannerTMapper.selectList(null);
        for(MoocBannerT moocBanner:moocBannerTS){
            BannerVo bannerVo = new BannerVo();
            bannerVo.setBannerId(moocBanner.getUuid()+"");
            bannerVo.setBannerUrl(moocBanner.getBannerUrl());
            bannerVo.setBannerAddress(moocBanner.getBannerAddress());
            result.add(bannerVo);
        }
        return result;
    }

    @Override
    public HotFilmVo getHotFilmVos(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId) {
        HotFilmVo hotFilmVo = new HotFilmVo();
        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper =new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        if(isLimit){
            Page<MoocFilmT> page = new Page(1,nums);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page,entityWrapper);
            filmInfos = getFilmInfo( moocFilmTS, filmInfos);
            hotFilmVo.setFilmNum(filmInfos.size());
            hotFilmVo.setFilmInfo(filmInfos);
        }else{
// 如果不是，则是列表页，同样需要限制内容为热映影片
            Page<MoocFilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            // 1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId){
                case 1 :
                    page = new Page<>(nowPage,nums,"film_box_office");
                    break;
                case 2 :
                    page = new Page<>(nowPage,nums,"film_time");
                    break;
                case 3 :
                    page = new Page<>(nowPage,nums,"film_score");
                    break;
                default:
                    page = new Page<>(nowPage,nums,"film_box_office");
                    break;
            }

            // 如果sourceId,yearId,catId 不为99 ,则表示要按照对应的编号进行查询
            if(sourceId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            if(catId != 99){
                // #2#4#22#
                String catStr = "%#"+catId+"#%";
                entityWrapper.like("film_cats",catStr);
            }

            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            // 组织filmInfos
            filmInfos = getFilmInfo( moocFilms, filmInfos);
            hotFilmVo.setFilmNum(filmInfos.size());
            int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts/nums)+1;
            hotFilmVo.setFilmInfo(filmInfos);
            hotFilmVo.setTotalPage(totalPages);
            hotFilmVo.setNowPage(nowPage);
        }
        return hotFilmVo;
    }

    @Override
    public HotFilmVo getSoonFilmVo(boolean isLimit,int nums,int nowPage,int sortId,int sourceId,int yearId,int catId) {
        HotFilmVo hotFilmVo = new HotFilmVo();
        List<FilmInfo> filmInfos = new ArrayList<>();
        EntityWrapper<MoocFilmT> entityWrapper =new EntityWrapper<>();
        entityWrapper.eq("film_status",1);
        if(isLimit){
            Page<MoocFilmT> page = new Page(2,nums);
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page,entityWrapper);
            filmInfos = getFilmInfo( moocFilmTS, filmInfos);
            hotFilmVo.setFilmNum(filmInfos.size());
            hotFilmVo.setFilmInfo(filmInfos);
        }else{
            // 如果不是，则是列表页，同样需要限制内容为即将上映影片
            Page<MoocFilmT> page = null;
            // 根据sortId的不同，来组织不同的Page对象
            // 1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (sortId){
                case 1 :
                    page = new Page<>(nowPage,nums,"film_preSaleNum");
                    break;
                case 2 :
                    page = new Page<>(nowPage,nums,"film_time");
                    break;
                case 3 :
                    page = new Page<>(nowPage,nums,"film_preSaleNum");
                    break;
                default:
                    page = new Page<>(nowPage,nums,"film_preSaleNum");
                    break;
            }

            // 如果sourceId,yearId,catId 不为99 ,则表示要按照对应的编号进行查询
            if(sourceId != 99){
                entityWrapper.eq("film_source",sourceId);
            }
            if(yearId != 99){
                entityWrapper.eq("film_date",yearId);
            }
            if(catId != 99){
                // #2#4#22#
                String catStr = "%#"+catId+"#%";
                entityWrapper.like("film_cats",catStr);
            }

            List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
            // 组织filmInfos
            filmInfos = getFilmInfo( moocFilms, filmInfos);
            hotFilmVo.setFilmNum(moocFilms.size());

            // 需要总页数 totalCounts/nums -> 0 + 1 = 1
            // 每页10条，我现在有6条 -> 1
            int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
            int totalPages = (totalCounts/nums)+1;

            hotFilmVo.setFilmInfo(filmInfos);
            hotFilmVo.setTotalPage(totalPages);
            hotFilmVo.setNowPage(nowPage);
        }
        return hotFilmVo;
    }

    @Override
    public HotFilmVo getClassicFilms(int nums, int nowPage, int sortId, int sourceId, int yearId, int catId) {
        HotFilmVo hotFilmVo = new HotFilmVo();
        List<FilmInfo> filmInfos = new ArrayList<>();

        // 即将上映影片的限制条件
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","3");

        // 如果不是，则是列表页，同样需要限制内容为即将上映影片
        Page<MoocFilmT> page = null;
        // 根据sortId的不同，来组织不同的Page对象
        // 1-按热门搜索，2-按时间搜索，3-按评价搜索
        switch (sortId){
            case 1 :
                page = new Page<>(nowPage,nums,"film_box_office");
                break;
            case 2 :
                page = new Page<>(nowPage,nums,"film_time");
                break;
            case 3 :
                page = new Page<>(nowPage,nums,"film_score");
                break;
            default:
                page = new Page<>(nowPage,nums,"film_box_office");
                break;
        }

        // 如果sourceId,yearId,catId 不为99 ,则表示要按照对应的编号进行查询
        if(sourceId != 99){
            entityWrapper.eq("film_source",sourceId);
        }
        if(yearId != 99){
            entityWrapper.eq("film_date",yearId);
        }
        if(catId != 99){
            // #2#4#22#
            String catStr = "%#"+catId+"#%";
            entityWrapper.like("film_cats",catStr);
        }

        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page, entityWrapper);
        // 组织filmInfos
        filmInfos = getFilmInfo( moocFilms, filmInfos);
        hotFilmVo.setFilmNum(moocFilms.size());

        // 需要总页数 totalCounts/nums -> 0 + 1 = 1
        // 每页10条，我现在有6条 -> 1
        int totalCounts = moocFilmTMapper.selectCount(entityWrapper);
        int totalPages = (totalCounts/nums)+1;

        hotFilmVo.setFilmInfo(filmInfos);
        hotFilmVo.setTotalPage(totalPages);
        hotFilmVo.setNowPage(nowPage);

        return hotFilmVo;
    }

    public  List<FilmInfo> getFilmInfo(List<MoocFilmT> moocFilmTS,List<FilmInfo> filmInfos){
        for(MoocFilmT film: moocFilmTS){
            FilmInfo filmInfo1add = new FilmInfo();
            filmInfo1add.setBoxNum(film.getFilmBoxOffice());
            filmInfo1add.setExpectNum(film.getFilmPresalenum());
            filmInfo1add.setSore(film.getFilmScore());
            filmInfo1add.setImgAddress(film.getImgAddress());
            filmInfo1add.setFilmId(film.getUuid()+"");
            filmInfo1add.setFilmName(film.getFilmName());
            filmInfo1add.setFilmType(film.getFilmType());
            filmInfo1add.setFilmScore(film.getFilmScore());
            filmInfo1add.setShowTime(DateUtil.getDay(film.getFilmTime()));
            filmInfos.add(filmInfo1add);
        }
        return filmInfos;
    }
    @Override
    public List<FilmInfo> getBoxRanking() {
        // 条件 -> 即将上映的，预售前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");
        List<FilmInfo> filmInfos = new ArrayList<>();
        Page<MoocFilmT> page = new Page<>(1,10,"film_box_office");

        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page,entityWrapper);

        filmInfos = getFilmInfo(moocFilms,filmInfos);

        return filmInfos;
    }

    @Override
    public List<FilmInfo> getExpectRanking() {
        // 条件 -> 正在上映的，评分前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","2");
        List<FilmInfo> filmInfos = new ArrayList<>();
        Page<MoocFilmT> page = new Page<>(1,10,"film_preSaleNum");
        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page,entityWrapper);
        filmInfos = getFilmInfo(moocFilms,filmInfos);
        return filmInfos;
    }

    @Override
    public List<FilmInfo> getTop100() {
        // 条件 -> 正在上映的，评分前10名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");

        Page<MoocFilmT> page = new Page<>(1,10,"film_score");

        List<MoocFilmT> moocFilms = moocFilmTMapper.selectPage(page,entityWrapper);
        List<FilmInfo> filmInfos = new ArrayList<>();
        filmInfos = getFilmInfo(moocFilms,filmInfos);

        return filmInfos;
    }

    @Override
    public List<CatVo> getCats() {
        List<CatVo> cats = new ArrayList<>();
        // 查询实体对象 - MoocCatDictT
        List<MoocCatDictT> moocCats = moocCatDictTMapper.selectList(null);
        // 将实体对象转换为业务对象 - CatVO
        for(MoocCatDictT moocCatDictT : moocCats){
            CatVo catVO = new CatVo();
            catVO.setCatId(moocCatDictT.getUuid()+"");
            catVO.setCatName(moocCatDictT.getShowName());
            cats.add(catVO);
        }
        return cats;
    }

    @Override
    public List<SourceVO> getSources() {
        List<SourceVO> sources = new ArrayList<>();
        List<MoocSourceDictT> moocSourceDicts = moocSourceDictTMapper.selectList(null);
        for(MoocSourceDictT moocSourceDictT : moocSourceDicts){
            SourceVO sourceVO = new SourceVO();

            sourceVO.setSourceId(moocSourceDictT.getUuid()+"");
            sourceVO.setSourceName(moocSourceDictT.getShowName());

            sources.add(sourceVO);
        }
        return sources;
    }

    @Override
    public List<YearVO> getYears() {
        List<YearVO> years = new ArrayList<>();
        // 查询实体对象 - MoocCatDictT
        List<MoocYearDictT> moocYears = moocYearDictTMapper.selectList(null);
        // 将实体对象转换为业务对象 - CatVO
        for(MoocYearDictT moocYearDictT : moocYears){
            YearVO yearVO = new YearVO();
            yearVO.setYearId(moocYearDictT.getUuid()+"");
            yearVO.setYearName(moocYearDictT.getShowName());

            years.add(yearVO);
        }
        return years;
    }

    @Override
    public FilmDetailVO getFilmDetail(int searchType, String searchParam) {
        FilmDetailVO filmDetailVO = null;
        // searchType 1-按名称  2-按ID的查找
        if(searchType == 1){
            filmDetailVO = moocFilmTMapper.getFilmDetailByName("%"+searchParam+"%");
        }else{
            filmDetailVO = moocFilmTMapper.getFilmDetailById(searchParam);
        }

        return filmDetailVO;
    }



}
