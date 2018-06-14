package com.scut.longerwu.graduation.dao;

import com.scut.longerwu.graduation.models.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface AssessItemDao {
    List<AssessItem> getAssessItemListByStandardId(@Param("standardId") Integer standardId);

    void insertAssessItemList(List<AssessItem> assessItemList);

    void update(@Param("id") Integer id,
                @Param("gnumber") String gnumber,
                @Param("gindex") String gindex,
                @Param("gcontent") String gcontent,
                @Param("result") String result);

    AssessItem getHistoryAssessItem(@Param("fileName") String fileName,
                                    @Param("itemName") String itemName);
}
