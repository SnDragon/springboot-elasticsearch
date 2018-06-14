package com.scut.longerwu.graduation.dao;

import com.scut.longerwu.graduation.models.*;
import org.apache.ibatis.annotations.*;

import java.util.*;

@Mapper
public interface QiyeStandardDao {

    void insert(QiyeStandard standard);

    QiyeStandard getQiyeStandardById(@Param("id") Integer id);

    void update(QiyeStandard standard);

    List<QiyeStandard> getStandardList();

    void updateStandardStatus(@Param("id") Integer id,
                              @Param("status") Integer status);
}
