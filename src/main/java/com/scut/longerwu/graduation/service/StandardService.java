package com.scut.longerwu.graduation.service;

import com.scut.longerwu.graduation.models.*;

import java.util.*;

public interface StandardService {

    void insertQiyeStandard(QiyeStandard standard);

    QiyeStandard getQiyeStandardById(Integer id);

    void addAssessmentRecord(AssessmentRecord record);

    List<QiyeStandard> getStandardList();

    void updateStandardStatus(Integer id, Integer status);
}
