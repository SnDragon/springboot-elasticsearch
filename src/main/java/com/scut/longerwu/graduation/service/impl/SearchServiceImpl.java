package com.scut.longerwu.graduation.service.impl;


import com.scut.longerwu.graduation.dao.*;
import com.scut.longerwu.graduation.enums.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import com.scut.longerwu.graduation.vo.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;
import java.util.regex.*;

@Service("searchService")
public class SearchServiceImpl implements SearchService {

    @Resource
    private StandardInfoDao standardInfoDao;
    @Resource
    private StandardInfoService standardInfoService;

    @Resource
    private ESDocumentService esDocumentService;

    @Resource
    private StandardMongoDao standardMongoDao;

    /**
     * 按产品搜索
     * @param product
     * @return
     */
    @Override
    public Result searchByProduct(String product) {
        Set<String> icsSet = new HashSet<>();
        List<SearchResultItem> searchResultItemList=standardMongoDao.getStandardResultItemListByProduct(product,icsSet);
        Set<RelatedResultItem> relatedResultItems=new HashSet<>();
        //根据国家标准分类号获取相关的国家标准
        for(String ics:icsSet){
            relatedResultItems.addAll(standardMongoDao.getRelatedResultItemsByIcs(ics));
        }
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("searchResult",searchResultItemList);
        resultMap.put("relatedResult",relatedResultItems);
        return new Result(ResultEnum.SUCCESS,resultMap);
    }
//    @Override
//    public Result searchByProduct(String product) {
//        List<StandardInfo> standardInfoList = standardInfoDao.getStandardInfoListByKeywords(product);
//        List<SearchResultItem> searchResultItemList = new ArrayList<>();
//        Set<String> icsSet = new HashSet<>();
//        for (StandardInfo standardInfo : standardInfoList) {
//            String fileName = standardInfo.getNumber() + " " + standardInfo.getcName();
//            String refStandards = standardInfo.getRefStandards();
//            SearchResultItem item = new SearchResultItem();
//            item.setNumber(standardInfo.getNumber());
//            item.setFileName(fileName);
//            String[] refNames = refStandards.split(";");
//            List<Reference> referenceList = new ArrayList<>();
//            for (String name : refNames) {
//                Reference reference = new Reference();
//                reference.setName(name);
//                referenceList.add(reference);
//            }
//            item.setReferences(referenceList);
//            List<StandardResultItem> standardResultItemList =
//                    standardMongoDao.getStandardResultItemListByFileName(standardInfo.getNumber());
//            item.setStandards(standardResultItemList);
//            searchResultItemList.add(item);
//            if (!"".equals(standardInfo.getIcs().trim())) {
//                icsSet.add(standardInfo.getIcs().trim());
//            }
//        }
//        System.out.println(icsSet);
//        List<StandardInfo> standardInfos = standardInfoService.getStandardInfoListByIcs(icsSet);
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("searchResult", searchResultItemList);
//        resultMap.put("relatedResult", standardInfos);
//        return new Result(ResultEnum.SUCCESS, resultMap);
//
//    }

    /**
     * 根据指标搜索
     * @param keywords
     * @return
     */
    @Override
    public Result searchByIndex(String keywords) {
//        List<ESResultItem> esResultItemList=esDocumentService.searchByIndex(keywords);
//        List<SearchResultItem> standardResultItemList=getResultItemList(keywords,esResultItemList);
        List<SearchResultItem> standardResultItemList = standardMongoDao.getStandardResultItemListByIndex(keywords, null);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("searchResult", standardResultItemList);
        resultMap.put("relatedResult", new ArrayList<>());
        return new Result(ResultEnum.SUCCESS, resultMap);
//        return new Result(ResultEnum.SUCCESS,esResultItemList);
    }

    /**
     * 混合搜索
     * @param product   产品
     * @param index     指标
     * @return
     */
    @Override
    public Result searchByMixing(String product, String index) {
//        List<ESResultItem> esResultItemList=esDocumentService.searchByMixing(product,index);
//        List<SearchResultItem> standardResultItemList=getResultItemList(index,esResultItemList);
        List<SearchResultItem> standardResultItemList = standardMongoDao.getStandardResultItemListByIndex(index, product);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("searchResult", standardResultItemList);
        resultMap.put("relatedResult", new ArrayList<>());
        return new Result(ResultEnum.SUCCESS, resultMap);
    }

    /**
     * 全文检索
     * @param keywords  检索关键词
     * @return
     */
    @Override
    public Result searchByFullText(String keywords) {
        List<Map<String, Object>> result = esDocumentService.searchByContent(keywords);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("searchResult", result);
        return new Result(ResultEnum.SUCCESS, resultMap);
    }

    /**
     * 高级查询
     * @param number
     * @param cName
     * @param ics
     * @param ccs
     * @param status
     * @param publishDateStart
     * @param publishDateEnd
     * @param carryOutDateStart
     * @param carryOutDateEnd
     * @param index
     * @return
     */
    @Override
    public List<AdvancedSearchResultItem> getAdvancedSearchResult(String number, String cName, String ics, String ccs, String[] status, Long publishDateStart, Long publishDateEnd, Long carryOutDateStart, Long carryOutDateEnd, String index) {
        List<AdvancedSearchResultItem> resultItemList = standardMongoDao.getAdvancedSearchResult(number, cName, ics, ccs, status, publishDateStart, publishDateEnd, carryOutDateStart, carryOutDateEnd, index);
        return resultItemList;
    }

//    @Override
//    public Result searchByIndex2(String keywords) {
//        SearchResultItem standardResultItem = standardMongoDao.getStandardResultItemListByIndex2(keywords);
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("searchResult", standardResultItem);
//        resultMap.put("relatedResult", new ArrayList<>());
//        return new Result(ResultEnum.SUCCESS, resultMap);
//    }

//    private List<SearchResultItem> getResultItemList(String keywords,List<ESResultItem> esResultItemList){
//        String str="<tag>"+keywords+"</tag>";
//        Pattern pattern=Pattern.compile("(\\d+(?:\\.\\d+)*)(?: )*"+str);
//
//        List<SearchResultItem> standardResultItemList=new ArrayList<>();
//
//        for(ESResultItem item: esResultItemList){
//            String highlights=item.getHighlights();
//            Matcher matcher=pattern.matcher(highlights);
//            if(matcher.find()){
//                String fileName=item.getTitle();
//                //TODO 可能为null
//                StandardInfo standardInfo=standardInfoDao.getStandardInfoByNumber(fileName);
//                SearchResultItem searchResultItem=new SearchResultItem();
//                fileName=fileName+" "+standardInfo.getcName();
//                searchResultItem.setFileName(fileName);
//                searchResultItem.setNumber(item.getTitle());
//                String[] refNames=standardInfo.getRefStandards().split(";");
//                List<Reference> referenceList=new ArrayList<>();
//                for(String name: refNames){
//                    Reference reference=new Reference();
//                    reference.setName(name);
//                    referenceList.add(reference);
//                }
//                searchResultItem.setReferences(referenceList);
//                String index=matcher.group(1);
//
//                StandardResultItem standardResultItem=standardMongoDao.getStandardResultItem(item.getTitle(),index);
//                List<StandardResultItem> list=new ArrayList<>();
//                list.add(standardResultItem);
//                searchResultItem.setStandards(list);
//                if(standardResultItem!=null){
//                    standardResultItemList.add(searchResultItem);
//                }
//            }
//        }
//        return standardResultItemList;
//    }
}
