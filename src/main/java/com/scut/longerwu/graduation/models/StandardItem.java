package com.scut.longerwu.graduation.models;

public class StandardItem {
    private String id;
    private String name;
    private String content;
    private String seNum;
    private Integer level;
    private String lastLevelName;
    private String fileName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSeNum() {
        return seNum;
    }

    public void setSeNum(String seNum) {
        this.seNum = seNum;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getLastLevelName() {
        return lastLevelName;
    }

    public void setLastLevelName(String lastLevelName) {
        this.lastLevelName = lastLevelName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
