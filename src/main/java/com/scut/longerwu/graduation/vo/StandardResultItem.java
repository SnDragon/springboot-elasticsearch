package com.scut.longerwu.graduation.vo;

import org.bson.*;

public class StandardResultItem {
    private String name;
    private String content;
    private String index;

    public StandardResultItem(){
        this.name="";
        this.content="";
        this.index="";
    }

    public StandardResultItem(String name, String content, String index) {
        this.name = name;
        this.content = content;
        this.index = index;
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public static Document document(StandardResultItem item){
        Document doc = new Document();
        doc.put("name",item.getName()!=null?item.getName():"");
        doc.put("index",item.getIndex()!=null?item.getIndex():"");
        doc.put("content",item.getContent()!=null?item.getContent():"");
        return doc;
    }

    @Override
    public String toString() {
        return "StandardResultItem{" +
                "index='" + index + '\'' +
                ", name='" + name + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
