package com.scut.longerwu.graduation.service.impl;

import com.scut.longerwu.graduation.dao.*;
import com.scut.longerwu.graduation.enums.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.annotation.*;
import java.util.*;


@Service("standardService")
public class StandardServiceImpl implements StandardService{

    @Resource
    private QiyeStandardDao qiyeStandardDao;
    @Resource
    private AssessmentRecordDao assessmentRecordDao;

    @Override
    public void insertQiyeStandard(QiyeStandard standard) {
        qiyeStandardDao.insert(standard);
    }

    @Override
    public QiyeStandard getQiyeStandardById(Integer id) {
        return qiyeStandardDao.getQiyeStandardById(id);
    }

    @Override
    @Transactional
    public void addAssessmentRecord(AssessmentRecord record) {
        QiyeStandard standard=new QiyeStandard();
        standard.setId(record.getStandardId());
        standard.setStatus(QiyeStandardEnum.DONE.getStatus());
        qiyeStandardDao.update(standard);
        assessmentRecordDao.add(record);
    }

    @Override
    public List<QiyeStandard> getStandardList() {
        return qiyeStandardDao.getStandardList();
    }

    @Override
    public void updateStandardStatus(Integer id, Integer status) {
        qiyeStandardDao.updateStandardStatus(id,status);
    }
}
