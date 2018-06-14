package com.scut.longerwu.graduation.models;

/**
 * 产品类别信息
 */
public class ProductTypeInfo {
    private Integer id;
    private String icsNum;      //ICS号
    private String cName;       //中文名
    private String eName;       //英文名
    private Integer level;      //级别

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcsNum() {
        return icsNum;
    }

    public void setIcsNum(String icsNum) {
        this.icsNum = icsNum;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}
