package com.foresttech.myblog.Utils;

public class Message {
    private Integer resCode;
    private String resDesc;
    private Object resData;

    public Integer getResCode() {
        return resCode;
    }

    public void setResCode(Integer resCode) {
        this.resCode = resCode;
    }

    public String getResDesc() {
        return resDesc;
    }

    public void setResDesc(String resDesc) {
        this.resDesc = resDesc;
    }

    public Object getResData() {
        return resData;
    }

    public void setResData(Object resData) {
        this.resData = resData;
    }

    public void setResHead(ResponseHead success) {
        this.resCode = success.getResCode();
        this.resDesc = success.getResDesc();
    }
}
