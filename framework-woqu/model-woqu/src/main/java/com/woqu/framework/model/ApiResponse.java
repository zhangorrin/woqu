package com.woqu.framework.model;

import java.io.Serializable;

/**
 * @author orrin on 2018/7/24
 */
public class ApiResponse<T extends Serializable> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int code;

    private T data;

    private String message;

    private int getCode() {
        return code;
    }

    private void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    private void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public static <T extends Serializable> ApiResponse success(T t){
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.setCode(200);
        apiResponse.setData(t);
        apiResponse.setMessage("success");
        return apiResponse;
    }

    public static <T extends Serializable> ApiResponse error(String message, T t){
        ApiResponse<T> apiResponse = new ApiResponse<T>();
        apiResponse.setCode(500);
        apiResponse.setData(t);
        apiResponse.setMessage(message);
        return apiResponse;
    }

    public static ApiResponse error(String message){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(500);
        apiResponse.setMessage(message);
        return apiResponse;
    }
}
