package cn.siney.entity;

import java.io.Serializable;

/**
 * @Author siney
 * @Date 2020/6/17 15:28
 * @Version 1.0
 */
public class Error implements Serializable {
    private String msg;

    public Error(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
