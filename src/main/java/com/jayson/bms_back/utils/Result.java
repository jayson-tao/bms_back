package com.jayson.bms_back.utils;

/**
 * ajax请求结果对象封装
 */
public class Result {
    private boolean success = true;
    private String message = "";
    private Object data = null;
    private static Result result = new Result();

    public Result(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Result() {
    }
    public static Result me (){
        return result;
    }

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, Object data) {
        this.success = success;
        this.data = data;
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
