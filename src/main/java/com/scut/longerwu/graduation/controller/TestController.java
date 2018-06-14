package com.scut.longerwu.graduation.controller;

import com.scut.longerwu.graduation.dao.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping(value = "/jobs")
public class TestController {
    @Resource
    private StandardMongoDao standardMongoDao;
    @Resource
    private StandardInfoDao standardInfoDao;

//    @RequestMapping(value = "/transforms")
//    public String transform(){
//        File file = new File("I:\\桌面\\LED\\广东标院-双层PDF");
//        File[] files = file.listFiles();
//        int count=0;
//        if(files!=null){
//            for(File fi:files){
//                String name = fi.getName();
//                if(name.endsWith(".txt")){
//                    name=name.substring(0,name.length()-5);
//                    List<StandardResultItem> standardResultItemList=standardMongoDao.getStandardResultItemListByFileName(name);
//                    if(standardResultItemList.size()!=0){
//                        name=name.replace("_","/");
//                        StandardInfo standardInfo=standardInfoDao.getStandardInfoByNumber(name);
//                        standardMongoDao.insertStandardDocument(standardInfo, standardResultItemList);
//                    }
//                }
//            }
//        }
//        System.out.println(count);
//
//        return "success";
//    }

//    @RequestMapping(value = "/transformData")
//    public String transformData(){
//        int total=0;
//        List<StandardInfo> standardInfoList=standardInfoDao.getStandardInfoList();
//        List<StandardResultItem> standardResultItemList=new ArrayList<>();
//        for(StandardInfo standardInfo:standardInfoList){
//            int count=standardMongoDao.getCountByNumber(standardInfo.getNumber());
//            if(count==0){
//                standardMongoDao.insertStandardDocument(standardInfo,standardResultItemList);
//            }
//        }
//        return "success";
//    }

//    @RequestMapping(value = "/add")
//    public String addStandards(@RequestParam(value = "times",defaultValue = "1")Integer times,
//                               @RequestParam(value = "type",defaultValue = "1")Integer type){
//        standardMongoDao.addStandards(times,type);
//        return "success";
//    }
}
