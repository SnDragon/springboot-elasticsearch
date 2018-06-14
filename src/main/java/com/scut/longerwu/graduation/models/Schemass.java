package com.scut.longerwu.graduation.models;

public class Schemass {

    private Integer id;
    private String title;
    private Integer level;
    private String content;
    private String lastLevelName;
    private String fileName;
    private String number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Schemass{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", level=" + level +
                ", content='" + content + '\'' +
                ", lastLevelName='" + lastLevelName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
