package com.scut.longerwu.graduation.vo;

import com.scut.longerwu.graduation.models.*;

import java.util.*;

public class SearchResultItem {
    private String number;
    private String fileName;
    private List<Reference> references;
    private List<StandardResultItem> standards;

    public SearchResultItem(){
        this.number="";
        this.fileName="";
    }

    public SearchResultItem(AssessItem item) {

    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Reference> getReferences() {
        return references;
    }

    public void setReferences(List<Reference> references) {
        this.references = references;
    }

    public List<StandardResultItem> getStandards() {
        return standards;
    }

    public void setStandards(List<StandardResultItem> standards) {
        this.standards = standards;
    }
}
