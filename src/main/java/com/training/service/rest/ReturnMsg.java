package com.training.service.rest;

/**
 * @author zhangliujie
 * @Description
 * @date 2019/9/10.
 */
public class ReturnMsg {

    private String data;

    private Integer code;

    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
