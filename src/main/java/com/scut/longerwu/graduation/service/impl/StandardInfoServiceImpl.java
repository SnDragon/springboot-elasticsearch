package com.scut.longerwu.graduation.service.impl;

import com.scut.longerwu.graduation.dao.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.util.*;

@Service("standardInfoService")
public class StandardInfoServiceImpl implements StandardInfoService {

    @Resource
    private StandardInfoDao standardInfoDao;

    @Override
    public List<StandardInfo> getStandardInfoListByIcs(Set<String> icsSet) {
        List<StandardInfo> standardInfoList=new ArrayList<>();
        for(String ics: icsSet){
            if("".equals(ics.trim())){
                continue;
            }
            String[] arr=ics.split("\\.");
            String searchIcs=ics;
            if(arr.length==3){
                searchIcs=arr[0]+"."+arr[1];
            }
            List<StandardInfo> standardInfos=standardInfoDao.getStandardInfoListByIcs(searchIcs);
            standardInfoList.addAll(standardInfos);
        }
        return standardInfoList;
    }

    @Override
    public StandardInfo getStandardById(Integer id) {
        return standardInfoDao.getStandardById(id);
    }

    @Override
    public StandardInfo getStandardByNumber(String number) {
        return standardInfoDao.getStandardInfoByNumber(number);
    }
}
