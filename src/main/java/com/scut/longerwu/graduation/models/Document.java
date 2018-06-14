package com.scut.longerwu.graduation.models;

import org.elasticsearch.index.similarity.*;
import org.springframework.format.annotation.*;

import java.util.*;

public class Document {

    private String id;
    private String title;
    private String author;
    private String content;
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss" )
    private Date publicDate;
    private Integer wordCount;


    public Document(){

    }

    public Document(String title, String author, String content, Integer wordCount) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.wordCount = wordCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public Integer getWordCount() {
        return wordCount;
    }

    public void setWordCount(Integer wordCount) {
        this.wordCount = wordCount;
    }
}
