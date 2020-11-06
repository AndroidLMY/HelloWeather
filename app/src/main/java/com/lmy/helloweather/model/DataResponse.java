package com.lmy.helloweather.model;


/**
 * @author lmy
 * @功能: 返回结果统一处理的Response类
 * @Creat 2020/11/6 3:56 PM
 * @Compony 465008238@qq.com
 */
public class DataResponse<T> {
    private int code;
    private String message;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
/**
 * 0: 成功
 * 1000: 非法操作
 * 1001: 参数错误
 * 1002: 请求超时
 * 1003: 参数签名错误
 * 1004: 验证码错误
 * 1005: 文件格式错误
 * 1006: 文件大小超过2m
 * 1007: 上传失败
 */
