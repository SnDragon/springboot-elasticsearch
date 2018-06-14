package com.scut.longerwu.graduation.models;

/**
 * 评价结果
 */
public class AssessmentRecord {
    private Integer id;
    private Integer standardId;     //企业标准id
    private String result;          //评价结果
    private Integer staffId;        //评价者id
    private String staffName;       //评价者姓名
    private Long assessTime;        //评价时间

    public AssessmentRecord(){

    }

    public AssessmentRecord(Integer standardId,String result,Integer staffId,String staffName,Long assessTime){
        this.standardId=standardId;
        this.result=result;
        this.staffId=staffId;
        this.staffName=staffName;
        this.assessTime=assessTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStandardId() {
        return standardId;
    }

    public void setStandardId(Integer standardId) {
        this.standardId = standardId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getAssessTime() {
        return assessTime;
    }

    public void setAssessTime(Long assessTime) {
        this.assessTime = assessTime;
    }
}