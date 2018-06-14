package com.scut.longerwu.graduation.dao;

import com.scut.longerwu.graduation.models.*;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AssessmentRecordDao {
    void add(AssessmentRecord record);
}
