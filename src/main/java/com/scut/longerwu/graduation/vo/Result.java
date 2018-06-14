package com.scut.longerwu.graduation.vo;

import com.scut.longerwu.graduation.enums.*;

public class Result<T> {
    private int code;
    private String msg;
    private T data;

    public Result(){
        this(ResultEnum.SUCCESS);
    }

    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(ResultEnum resultEnum){
        this.code=resultEnum.getCode();
        this.msg=resultEnum.getMsg();
    }


    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(ResultEnum resultEnum,T data){
        this(resultEnum);
        this.data=data;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
