package com.scut.longerwu.graduation.dao;

import com.scut.longerwu.graduation.models.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface StandardInfoDao {

    List<StandardInfo> getStandardInfoListByKeywords(@Param("keywords") String keywords);

    StandardInfo getStandardInfoByNumber(@Param("number") String fileName);

    List<StandardInfo> getStandardInfoListByIcs(@Param("ics")String ics);

    StandardInfo getStandardById(@Param("id") Integer id);

    List<StandardInfo> getStandardInfoList();
}
