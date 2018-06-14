package com.scut.longerwu.graduation.jobs;


import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

import javax.annotation.*;
import java.io.*;
import java.util.*;

/**
 * 更新同义词库,每10min更新一次
 */
@Component
public class SynonymService {

    @Value("${file.synonym}")
    private String synonymFile;

    private List<String> synonymLines;

    private final Logger LOGGER=Logger.getLogger(SynonymService.class);

    private final long INTERNAL_TIME=10*60*1000; //10min

    @Scheduled(cron = "0 0/10 * * * ?")
    public void scheduled(){
        File file=new File(synonymFile);
        long lastModifiedTime = file.lastModified();
        long currentTime=System.currentTimeMillis();
        if(currentTime-lastModifiedTime<INTERNAL_TIME){
            LOGGER.info("update synonym..."+System.currentTimeMillis());
            init();
        }
    }

    /**
     * 初始化
     */
    @PostConstruct
    public void init(){
        List<String> lines=new ArrayList<>();
        //读取同义词
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(synonymFile));
            String line=null;
            while((line=bufferedReader.readLine())!=null){
                lines.add(line);
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error:"+e.getMessage());
        }
        this.synonymLines=lines;
        for (String line:synonymLines){
            System.out.println(line);
        }
    }

    public String[] getSynonyms(String keyword){
        for(String line:synonymLines){
            if(line.matches(".*"+keyword+".*")){
                return line.split(":");
            }
        }
        return null;
    }
}
