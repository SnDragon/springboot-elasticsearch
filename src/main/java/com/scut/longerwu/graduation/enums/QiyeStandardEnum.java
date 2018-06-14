package com.scut.longerwu.graduation.enums;

/**
 * 企业标准状态
 */
public enum QiyeStandardEnum {
    UPLOADED(1),    //未评审
    DOING(2),       //评审中
    DONE(3);        //评审完成

    private int status;

    QiyeStandardEnum(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
