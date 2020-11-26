package com.foresttech.myblog.Utils;

import com.alibaba.fastjson.JSONObject;

public class Message {
    private Integer resCode;
    private String resDesc;
    private Object resData;
    private String resToken;

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

    public Message setResData(Object resData) {
        this.resData = resData;
        return this;
    }

    public Message setResHead(ResponseHead success) {
        this.resCode = success.getResCode();
        this.resDesc = success.getResDesc();
        return this;
    }

    public String toJsonString() {
        return JSONObject.toJSONString(this);
    }

    public String getResToken() {
        return resToken;
    }

    public void setResToken(String resToken) {
        this.resToken = resToken;
    }
}
