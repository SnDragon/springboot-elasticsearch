package com.scut.longerwu.graduation.models;

public class StandardInfo {
    private Integer id;
    private String number;
    private String cName;
    private String eName;
    private String applicableRange;
    private String ccs;
    private String ics;
    private String status;
    private String refStandards;
    private String carryOutDate;
    private String publishDate;
    private String abolishDate;
    private String scope;

    public StandardInfo() {
        this.number = this.cName = this.eName = this.applicableRange = this.ccs
                = this.ics = this.status = this.refStandards = this.carryOutDate
                = this.publishDate = this.abolishDate = this.scope = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getApplicableRange() {
        return applicableRange;
    }

    public void setApplicableRange(String applicableRange) {
        this.applicableRange = applicableRange;
    }

    public String getCcs() {
        return ccs;
    }

    public void setCcs(String ccs) {
        this.ccs = ccs;
    }

    public String getIcs() {
        return ics;
    }

    public void setIcs(String ics) {
        this.ics = ics;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRefStandards() {
        return refStandards;
    }

    public void setRefStandards(String refStandards) {
        this.refStandards = refStandards;
    }

    public String getCarryOutDate() {
        return carryOutDate;
    }

    public void setCarryOutDate(String carryOutDate) {
        this.carryOutDate = carryOutDate;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getAbolishDate() {
        return abolishDate;
    }

    public void setAbolishDate(String abolishDate) {
        this.abolishDate = abolishDate;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
