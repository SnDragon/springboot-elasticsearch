package com.scut.longerwu.graduation.vo;

public class RelatedResultItem {
    private String number;
    private String cName;

    public RelatedResultItem(){

    }

    public RelatedResultItem(String number,String cName){
        this.number=number;
        this.cName=cName;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RelatedResultItem that = (RelatedResultItem) o;

        return number != null ? number.equals(that.number) : that.number == null;

    }

    @Override
    public int hashCode() {
        return number != null ? number.hashCode() : 0;
    }
}
