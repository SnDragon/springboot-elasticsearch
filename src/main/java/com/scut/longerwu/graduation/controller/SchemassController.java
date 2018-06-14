package com.scut.longerwu.graduation.controller;

import com.scut.longerwu.graduation.dao.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;

@RestController
public class SchemassController {

    @Autowired
    private SchemassDao schemassDao;

    @Resource
    private StandardInfoDao standardInfoDao;

    @Resource
    private ESDocumentService esDocumentService;

    @GetMapping("/schemasses/schemass/{id}")
    public Schemass getShemass(@PathVariable("id") Integer id) {
        Schemass schemass = schemassDao.findSchemassById(id);
        System.out.println(schemass);
        return schemass;
    }

    /**
     * 将txt格式的国家标准文本存到ES中
     */
    @GetMapping("/schemasses/batch")
    public String doInsert() throws Exception {
        File file = new File("I:\\桌面\\LED\\广东标院-双层PDF");
        File[] files = file.listFiles();
        if(files!=null){
            for(File fi:files){
                String path = fi.getAbsolutePath();
                String name = fi.getName();
                if(name.endsWith(".txt")){
                    //查询数据库,整合完整的文章信息
                    List<Schemass> schemassList=schemassDao.findSchemassByName(name);
                    StringBuilder sb=new StringBuilder();
                    if(schemassList!=null && schemassList.size()>0){
                        for(Schemass schemass:schemassList){
                            sb.append(schemass.getNumber().replaceAll(" ","")+" "+schemass.getTitle()+"\n"+schemass.getContent()+"\n");
                        }
                        PrintWriter writer = new PrintWriter("I:\\桌面\\test\\"+name, "UTF-8");
                        writer.println(sb);
                        writer.close();
//                        Document document=new Document(name.substring(0,name.length()-4),"test",sb.toString(),sb.length());
//                        esDocumentService.addDocument(document);
                        StandardInfo standardInfo=standardInfoDao.getStandardInfoByNumber(name.substring(0,name.length()-5).replace("_","/"));
                        Standard standard=new Standard(standardInfo,sb.toString());
                        esDocumentService.addStandard(standard);
                    }
                }
            }
        }

        return "success";

    }

}
