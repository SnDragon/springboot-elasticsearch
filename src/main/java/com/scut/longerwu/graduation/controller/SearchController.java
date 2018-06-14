package com.scut.longerwu.graduation.controller;

import com.scut.longerwu.graduation.enums.*;
import com.scut.longerwu.graduation.service.*;
import com.scut.longerwu.graduation.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import java.lang.reflect.*;
import java.util.*;

@RestController
@RequestMapping(value = "/api")
public class SearchController {

    @Resource
    private SearchService searchService;

    @GetMapping(value = "/search")
    public Result search(@RequestParam("searchType") String searchType,
                         @RequestParam("keywords") String keywords) {
        if ("".equals(keywords.trim())) {
            Map<String,Object> resultMap=new HashMap<>();
            resultMap.put("searchResult",new ArrayList<>());
            resultMap.put("relatedResult",new ArrayList<>());
            return new Result(ResultEnum.SUCCESS,resultMap);
        }
        if (searchType.equals("product")) {
            //按产品名称搜索
            return searchService.searchByProduct(keywords);
        } else if (searchType.equals("norm")) {
            //按指标名称搜索
            return searchService.searchByIndex(keywords);
        } else if (searchType.equals("mixing")) {
            String[] arr = keywords.split(" ");
            if (arr == null || arr.length != 2) {
                return new Result(ResultEnum.PARAM_ERROR);
            }
            return searchService.searchByMixing(arr[0], arr[1]);
        } else if (searchType.equals("fullText")) {
            return searchService.searchByFullText(keywords);
        } else {
            Map<String,Object> resultMap=new HashMap<>();
            resultMap.put("searchResult",new ArrayList<>());
            resultMap.put("relatedResult",new ArrayList<>());
            return new Result(ResultEnum.SUCCESS,resultMap);
        }
    }

    @RequestMapping(value = "/advancedSearch")
    public Result advancedSearch(@RequestParam(value = "number", required = false) String number,
                                 @RequestParam(value = "cName", required = false) String cName,
                                 @RequestParam(value = "ics", required = false) String ics,
                                 @RequestParam(value = "ccs", required = false) String ccs,
                                 @RequestParam(value = "status[]", required = false) String[] status,
                                 @RequestParam(value = "publishDateStart", required = false) Long publishDateStart,
                                 @RequestParam(value = "publishDateEnd", required = false) Long publishDateEnd,
                                 @RequestParam(value = "carryOutDateStart", required = false) Long carryOutDateStart,
                                 @RequestParam(value = "carryOutDateEnd", required = false) Long carryOutDateEnd,
                                 @RequestParam(value = "index", required = false) String index) {
        if(status==null){
            status=new String[]{};
        }
        List<AdvancedSearchResultItem> itemList =
                searchService.getAdvancedSearchResult(number, cName, ics, ccs, status, publishDateStart,publishDateEnd,carryOutDateStart, carryOutDateEnd, index);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("searchResult", itemList);
        return new Result(ResultEnum.SUCCESS, resultMap);
    }

//    @RequestMapping(value = "/searchTest")
//    public Result testSearch(@RequestParam("keywords")String keywords){
//        return searchService.searchByIndex2(keywords);
//    }

}
