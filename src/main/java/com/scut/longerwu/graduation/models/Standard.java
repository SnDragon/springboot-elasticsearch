package com.scut.longerwu.graduation.models;

public class Standard {
    private StandardInfo standardInfo;
    private String content;

    public Standard(){

    }

    public Standard(StandardInfo standardInfo, String content) {
        if(standardInfo==null){
            standardInfo=new StandardInfo();
        }
        this.standardInfo = standardInfo;
        this.content = content;
    }

    public StandardInfo getStandardInfo() {
        return standardInfo;
    }

    public void setStandardInfo(StandardInfo standardInfo) {
        this.standardInfo = standardInfo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
