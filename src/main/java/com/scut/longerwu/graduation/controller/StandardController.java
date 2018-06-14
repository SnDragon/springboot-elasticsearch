package com.scut.longerwu.graduation.controller;

import com.scut.longerwu.graduation.dao.*;
import com.scut.longerwu.graduation.enums.*;
import com.scut.longerwu.graduation.jobs.*;
import com.scut.longerwu.graduation.models.*;
import com.scut.longerwu.graduation.service.*;
import com.scut.longerwu.graduation.util.*;
import com.scut.longerwu.graduation.vo.*;
import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.transaction.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.*;
import javax.servlet.http.*;
import java.util.*;

@RestController
public class StandardController {
    @Resource
    private StandardInfoService standardInfoService;
    @Resource
    private StandardMongoDao standardMongoDao;
    @Resource
    private StandardService standardService;
    @Resource
    private SynonymService synonymService;
    @Resource
    private AssessItemDao assessItemDao;

    @Value("${directory.qiyeTxt}")
    private String qiyeTxtDirectory;

    private final static Logger LOGGER=Logger.getLogger(StandardController.class);

    @GetMapping("/api/standards/standard/{id}")
    public Result getStandardById(@PathVariable("id") Integer id) {
        StandardInfo standardInfo = standardInfoService.getStandardById(id);
        return new Result(ResultEnum.SUCCESS, standardInfo);
    }

    @GetMapping("/api/standards/standard")
    public Result getStandardByNumber(@RequestParam("number") String number) {
        StandardInfo standardInfo = standardInfoService.getStandardByNumber(number);
        return new Result(ResultEnum.SUCCESS, standardInfo);
    }

    @RequestMapping(value = "/api/qiyeStandards/{id}")
    @Transactional
    public Result getQiyeStandardAssessmentInfo(@PathVariable("id") Integer id) {
        QiyeStandard standard = standardService.getQiyeStandardById(id);
        if(standard==null){
            return new Result(ResultEnum.PARAM_ERROR);
        }
        List<AssessItem> assessItemList=assessItemDao.getAssessItemListByStandardId(id);
        if(assessItemList==null || assessItemList.size()==0){
            String fileName=standard.getFileName();
            String filePath=qiyeTxtDirectory+fileName+".txt";
            List<StandardResultItem> itemList=null;
            List<String> refFileList=null;
            try {
                itemList = PdfboxUtil.extractRequirements(filePath);
                refFileList=PdfboxUtil.extractRefFiles(filePath);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("error:"+e.getStackTrace());
                return new Result(ResultEnum.SERVER_ERROR);
            }

            Collections.reverse(refFileList);

            //查找企业标准指标对应的国家标准内容
            assessItemList=new ArrayList<>();
            for(StandardResultItem item: itemList){
                AssessItem assessItem=null;
                //先查找历史评审记录
                AssessItem historyItem=assessItemDao.getHistoryAssessItem(standard.getOriginalFileName(),item.getName());
                if(historyItem!=null){
                    assessItem=new AssessItem(id,item,historyItem);
                }else{
                    SearchResultItem gItem=standardMongoDao.getStandardResultItem(item.getName(),refFileList);
                    if(gItem==null){
                        String[] synonyms=synonymService.getSynonyms(item.getName());
                        if(synonyms!=null){
                            for(String name:synonyms){
                                if(!item.getName().equals(name)){
                                    gItem=standardMongoDao.getStandardResultItem(name,refFileList);
                                    if(gItem!=null){
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    assessItem=new AssessItem(id,item,gItem);
                }

                assessItemList.add(assessItem);
            }
            //插入数据库
            assessItemDao.insertAssessItemList(assessItemList);
            //更新状态
            standardService.updateStandardStatus(standard.getId(),QiyeStandardEnum.DOING.getStatus());
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("standard", standard);
        resultMap.put("assessItemList", assessItemList);
        return new Result(ResultEnum.SUCCESS, resultMap);
    }

    @RequestMapping(value = "/api/assessItems/assessment/{id}",method = RequestMethod.POST)
    public Result updateAssessItems(@PathVariable("id")Integer id,
                                    @RequestParam(value = "gnumber",required = false)String gnumber,
                                    @RequestParam(value = "gindex",required = false)String gindex,
                                    @RequestParam(value = "gcontent",required = false)String gcontent,
                                    @RequestParam(value = "result",required = false)String result){
        assessItemDao.update(id,gnumber,gindex,gcontent,result);
        return new Result(ResultEnum.SUCCESS);
    }

    @RequestMapping(value = "/api/assessmentRecords/assessmentRecord",method = RequestMethod.POST)
    public Result addAssessmentRecord(@RequestParam("standardId")Integer standardId,
                                      @RequestParam("result")String result,
                                      HttpSession session){
        Integer staffId=1;
        Staff staff= (Staff) session.getAttribute("staff");
        String staffName= staff==null?"user1":staff.getName();
        Long currentTime=System.currentTimeMillis();
        AssessmentRecord record=new AssessmentRecord(standardId,result,staffId,staffName,currentTime);
        standardService.addAssessmentRecord(record);
        return new Result(ResultEnum.SUCCESS);
    }

    @RequestMapping(value = "/api/standards/list",method = RequestMethod.GET)
    public Result getStandardList(){
        List<QiyeStandard> standards=standardService.getStandardList();
        Map<String,Object> resultMap=new HashMap<>();
        resultMap.put("standards",standards);
        return new Result(ResultEnum.SUCCESS,resultMap);
    }

}
