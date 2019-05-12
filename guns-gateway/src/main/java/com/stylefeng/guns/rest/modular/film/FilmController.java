package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.stylefeng.guns.api.user.film.vo.*;
import com.stylefeng.guns.api.user.film.vo.service.FilmAsyncServiceApi;
import com.stylefeng.guns.api.user.film.vo.service.FilmService;
import com.stylefeng.guns.rest.modular.vo.FilmConditionVo;
import com.stylefeng.guns.rest.modular.vo.FilmIndexVo;
import com.stylefeng.guns.rest.modular.vo.FilmRequestVO;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * java类简单作用描述
 *
 * @Description: java类作用描述
 * @Author: 作者姓名
 * @CreateDate: 2019/4/4 14:58
 * @UpdateUser: jiaorongguo
 * @UpdateDate: 2019/4/4 14:58
 * @Version: 1.0
 * 身无彩凤双飞翼，心有灵犀一点通。
 */
@RestController
@RequestMapping("/film/")
public class FilmController {
    private static  final String  IMG_PRE= "http://img.jrgshop.cn";
    @Reference(interfaceClass = FilmService.class)
    private FilmService filmService;
    @Reference(interfaceClass = FilmAsyncServiceApi.class,async = true,check = false)
    private FilmAsyncServiceApi filmAsyncServiceApi;
    @PostMapping(value = "/index")
    public ResponseVO<FilmIndexVo> getIndex(){
        FilmIndexVo filmIndexVo = new FilmIndexVo();
        filmIndexVo.setBanners(filmService.getBanners());
        filmIndexVo.setBoxRanking(filmService.getBoxRanking());
        filmIndexVo.setExpectRanking(filmService.getExpectRanking());
        filmIndexVo.setHotFilmVo(filmService.getHotFilmVos(true,8,1,1,99,99,99));
        filmIndexVo.setSoonFilmVo(filmService.getSoonFilmVo(true,8,1,1,99,99,99));
        filmIndexVo.setTop100(filmService.getTop100());
        return ResponseVO.success(IMG_PRE,filmIndexVo);
    }

    @GetMapping (value = "/getIndexList")
    public ResponseVO getConditionList(@RequestParam (name = "catId",required = false,defaultValue = "99") String catId,
                                        @RequestParam (name = "sourceId",required = false,defaultValue = "99") String sourceId,
                                        @RequestParam (name = "yearId",required = false,defaultValue = "99") String yearId){
        FilmConditionVo filmConditionVO = new FilmConditionVo();
        // 标识位
        boolean flagf = false;
        // 类型集合
        List<CatVo> cats = filmService.getCats();
        List<CatVo> catResult = new ArrayList<>();
        CatVo cat = null;
        for(CatVo catVO : cats){
            // 判断集合是否存在catId，如果存在，则将对应的实体变成active状态
            // 6
            // 1,2,3,99,4,5 ->
            /*
                优化：【理论上】
                    1、数据层查询按Id进行排序【有序集合 -> 有序数组】
                    2、通过二分法查找
             */
            if(catVO.getCatId().equals("99")){
                cat = catVO;
                continue;
            }
            if(catVO.getCatId().equals(catId)){
                flagf = true;
                catVO.setActive(true);
            }else{
                catVO.setActive(false);
            }
            catResult.add(catVO);
        }
        // 如果不存在，则默认将全部变为Active状态
        if(!flagf){
            System.out.println("类型集合");
            cat.setActive(true);
            catResult.add(cat);
        }else{
            cat.setActive(false);
            catResult.add(cat);
        }
// 片源集合
        boolean flags=false;
        List<SourceVO> sources = filmService.getSources();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO sourceVO = null;
        for(SourceVO source : sources){
            if(source.getSourceId().equals("99")){
                sourceVO = source;
                continue;
            }
            if(source.getSourceId().equals(sourceId)){
                flags = true;
                source.setActive(true);
            }else{
                source.setActive(false);
            }
            sourceResult.add(source);
        }
        // 如果不存在，则默认将全部变为Active状态
        if(!flags){
            System.out.println("片源集合");
            sourceVO.setActive(true);
            sourceResult.add(sourceVO);
        }else{
            sourceVO.setActive(false);
            sourceResult.add(sourceVO);
        }

        // 年代集合
        boolean flagy=false;
        List<YearVO> years = filmService.getYears();
        List<YearVO> yearResult = new ArrayList<>();
        YearVO yearVO = null;
        for(YearVO year : years){
            if(year.getYearId().equals("99")){
                yearVO = year;
                continue;
            }
            if(year.getYearId().equals(yearId)){
                flagy = true;
                year.setActive(true);
            }else{
                year.setActive(false);
            }
            yearResult.add(year);
        }
        // 如果不存在，则默认将全部变为Active状态
        if(!flagy){

            yearVO.setActive(true);
            yearResult.add(yearVO);
        }else{
            yearVO.setActive(false);
            yearResult.add(yearVO);
        }
        filmConditionVO.setCatVos(catResult);
        filmConditionVO.setSourceVOS(sourceResult);
        filmConditionVO.setYearVOS(yearResult);
        return ResponseVO.success(filmConditionVO);

    }

    @RequestMapping(value = "getFilms",method = RequestMethod.GET)
    public ResponseVO getFilms(FilmRequestVO filmRequestVO){

        String img_pre = "http://img.meetingshop.cn/";

        HotFilmVo filmVO = null;
        // 根据showType判断影片查询类型
        switch (filmRequestVO.getShowType()){
            case 1 :
                filmVO = filmService.getHotFilmVos(
                        false,filmRequestVO.getPageSize(),filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),filmRequestVO.getSourceId(),filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            case 2 :
                filmVO = filmService.getSoonFilmVo(
                        false,filmRequestVO.getPageSize(),filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),filmRequestVO.getSourceId(),filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
            case 3 :
                filmVO = filmService.getClassicFilms(
                        filmRequestVO.getPageSize(),filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),filmRequestVO.getSourceId(),
                        filmRequestVO.getYearId(), filmRequestVO.getCatId());
                break;
            default:
                filmVO = filmService.getHotFilmVos(
                        false,filmRequestVO.getPageSize(),filmRequestVO.getNowPage(),
                        filmRequestVO.getSortId(),filmRequestVO.getSourceId(),filmRequestVO.getYearId(),
                        filmRequestVO.getCatId());
                break;
        }
        // 根据sortId排序
        // 添加各种条件查询
        // 判断当前是第几页
        return ResponseVO.success(
                filmVO.getNowPage(),filmVO.getTotalPage(),
                img_pre,filmVO.getFilmInfo());
    }

    @RequestMapping(value = "films/{searchParam}",method = RequestMethod.GET)
    public ResponseVO films(@PathVariable("searchParam")String searchParam,
                            int searchType) throws ExecutionException, InterruptedException {
        // 根据searchType，判断查询类型
        FilmDetailVO filmDetail = filmService.getFilmDetail(searchType, searchParam);
        if(filmDetail==null){
            return ResponseVO.serviceFail("没有可查询的影片");
        }else if(filmDetail.getFilmId()==null || filmDetail.getFilmId().trim().length()==0){
            return ResponseVO.serviceFail("没有可查询的影片");
        }
        String filmId = filmDetail.getFilmId();
        // 查询影片的详细信息 -> Dubbo的异步调用
        // 获取影片描述信息
//      FilmDescVO filmDescVO = filmAsyncServiceApi.getFilmDesc(filmId);
        filmAsyncServiceApi.getFilmDesc(filmId);
        Future<FilmDescVO> filmDescVOFuture = RpcContext.getContext().getFuture();
        // 获取图片信息
        filmAsyncServiceApi.getImgs(filmId);
        Future<ImgVO> imgVOFuture = RpcContext.getContext().getFuture();
        // 获取导演信息
        filmAsyncServiceApi.getDectInfo(filmId);
        Future<ActorVO> actorVOFuture = RpcContext.getContext().getFuture();
        // 获取演员信息
        filmAsyncServiceApi.getActors(filmId);
        Future<List<ActorVO>> actorsVOFutrue = RpcContext.getContext().getFuture();
        // 组织info对象
        InfoRequstVO infoRequstVO = new InfoRequstVO();
        // 组织Actor属性
        ActorRequestVO actorRequestVO = new ActorRequestVO();
        actorRequestVO.setActors(actorsVOFutrue.get());
        actorRequestVO.setDirector(actorVOFuture.get());
        // 组织info对象
        infoRequstVO.setActors(actorRequestVO);
        infoRequstVO.setBiography(filmDescVOFuture.get().getBiography());
        infoRequstVO.setFilmId(filmId);
        infoRequstVO.setImgVO(imgVOFuture.get());
        // 组织成返回值
        filmDetail.setInfo04(infoRequstVO);
        return ResponseVO.success("http://img.meetingshop.cn/",filmDetail);
    }

}
