package com.scut.longerwu.graduation.dao;


import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.vo.*;

import java.util.*;

public interface StandardMongoDao {
//    StandardResultItem getStandardResultItem(String fileName, String seNum);

    SearchResultItem getStandardResultItem(String name,List<String> refFiles);

    List<StandardResultItem> getStandardResultItemListByFileName(String fileName);

    void insertStandardDocument(StandardInfo standardInfo, List<StandardResultItem> standardResultItemList);

    List<SearchResultItem> getStandardResultItemListByIndex(String keywords,String product);

    List<AdvancedSearchResultItem> getAdvancedSearchResult(String number, String cName, String ics, String ccs, String[] status,  Long publishDateStart, Long publishDateEnd, Long carryOutDateStart, Long carryOutDateEnd, String index);

    int getCountByNumber(String number);

    List<SearchResultItem> getStandardResultItemListByProduct(String product,Set<String> icsSet);

    List<RelatedResultItem> getRelatedResultItemsByIcs(String ics);

//    SearchResultItem getStandardResultItemListByIndex2(String keywords);

//    void addStandards(Integer times, Integer type);
}
