package com.scut.longerwu.graduation.models;

import com.scut.longerwu.graduation.vo.*;

/**
 * 指标评价项
 */
public class AssessItem {
    private Integer id;
    private Integer standardId;     //评价的企业标准id
    private String qname;           //指标项
    private String qindex;          //原文章节
    private String qcontent;        //原文内容
    private String gnumber;         //对应的国家标准号
    private String gindex;          //对应的国家标准章节
    private String gcontent;        //对应的国家标准内容
    private String result;          //评价结果

    public AssessItem(){

    }

    public AssessItem(StandardResultItem item, SearchResultItem gItem) {
        this.qname=item.getName();
        this.qindex=item.getIndex();
        this.qcontent=item.getContent();
        if(gItem!=null){
            this.gnumber=gItem.getNumber()+" "+gItem.getFileName();
            StandardResultItem resultItem=gItem.getStandards().get(0);
            this.gindex=resultItem.getIndex()+" "+resultItem.getName();
            this.gcontent=resultItem.getContent();
        }else{
            this.gnumber="";
            this.gindex="";
            this.gcontent="";
        }
        this.result="";
    }

    public AssessItem(Integer standardId,StandardResultItem item, SearchResultItem gItem){
        this(item,gItem);
        this.standardId=standardId;
    }

    public AssessItem(Integer standardId,StandardResultItem item,AssessItem historyItem){
        this.standardId=standardId;
        this.qname=item.getName();
        this.qindex=item.getIndex();
        this.qcontent=item.getContent();
        this.gindex=historyItem.getGindex();
        this.gnumber=historyItem.getGnumber();
        this.gcontent=historyItem.getGcontent();
//        this.result=historyItem.getResult();
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

    public String getQname() {
        return qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    public String getQindex() {
        return qindex;
    }

    public void setQindex(String qindex) {
        this.qindex = qindex;
    }

    public String getQcontent() {
        return qcontent;
    }

    public void setQcontent(String qcontent) {
        this.qcontent = qcontent;
    }

    public String getGnumber() {
        return gnumber;
    }

    public void setGnumber(String gnumber) {
        this.gnumber = gnumber;
    }

    public String getGindex() {
        return gindex;
    }

    public void setGindex(String gindex) {
        this.gindex = gindex;
    }

    public String getGcontent() {
        return gcontent;
    }

    public void setGcontent(String gcontent) {
        this.gcontent = gcontent;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
