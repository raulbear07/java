package com.rr.blog.dto;

public class JsonResult<T> {
    private Boolean success;
    private Integer code;
    private String message;
    private T data;

    public JsonResult(Boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public JsonResult fail() {
        return new JsonResult(false, 1, "操作失败", null);
    }
    public JsonResult fail(String message) {
        return new JsonResult(false, 1, message, null);
    }
    public  JsonResult ok(){
        return new JsonResult(true,0,"操作成功",null);
    }
    public JsonResult ok(T t){
        return new JsonResult(true,0,"操作成功",t);
    }


}
