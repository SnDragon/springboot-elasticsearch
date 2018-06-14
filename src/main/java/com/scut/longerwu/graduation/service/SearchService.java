package com.scut.longerwu.graduation.service;

import com.scut.longerwu.graduation.vo.*;

import java.util.*;

public interface SearchService {

    Result searchByProduct(String product);

    Result searchByIndex(String index);

    Result searchByMixing(String product, String index);

    Result searchByFullText(String keywords);

    List<AdvancedSearchResultItem> getAdvancedSearchResult(String number, String cName, String ics, String ccs, String[] status, Long publishDateStart, Long publishDateEnd, Long carryOutDateStart, Long carryOutDateEnd, String index);

//    Result searchByIndex2(String keywords);
}
