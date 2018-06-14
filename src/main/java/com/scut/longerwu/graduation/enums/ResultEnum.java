package com.scut.longerwu.graduation.enums;

/**
 * 返回给前端的结果枚举
 */
public enum  ResultEnum {
    SUCCESS(0,"SUCCESS"),
    PARAM_ERROR(101,"参数错误"),
    FILE_FORMAT_ERROR(102,"文件格式有误!"),
    FAIL(400,"失败"),
    UNAUTHORIZED(401,"未授权"),
    LOGIN_FAIL(402,"登录失败"),
    SERVER_ERROR(500,"服务器错误");

    private int code;
    private String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
