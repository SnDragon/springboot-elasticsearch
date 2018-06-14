package com.scut.longerwu.graduation.models;

import com.scut.longerwu.graduation.enums.*;

/**
 * 企业标准
 */
public class QiyeStandard {
    private Integer id;
    private String originalFileName;    //原标准名称
    private String fileName;            //实际文件名
    private Long size;                  //文件大小
    private String uploadStaff;         //上传者
    private Long uploadTime;            //上传时间
    private Integer status;             //评审状态:上传(1)|对标(2)|完成(3)
    private String company;             //公司名称
    private AssessmentRecord assessment;

    public QiyeStandard(){

    }

    public QiyeStandard(String originalFileName, String fileName, Long size, String uploadStaff, Long uploadTime,String company) {
        this.originalFileName = originalFileName;
        this.fileName = fileName;
        this.size = size;
        this.uploadStaff = uploadStaff;
        this.uploadTime = uploadTime;
        this.status= QiyeStandardEnum.UPLOADED.getStatus();
        this.company=company;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getUploadStaff() {
        return uploadStaff;
    }

    public void setUploadStaff(String uploadStaff) {
        this.uploadStaff = uploadStaff;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public AssessmentRecord getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentRecord assessment) {
        this.assessment = assessment;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}