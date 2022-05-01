package com.example.demo.utils;


public class ApiResponse {
    private String respCode;
    private String message;
      private Object data;

    public ApiResponse() {

    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                ", respCode='" + respCode + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public ApiResponse(String respCode, String message, Object data) {
        this.respCode = respCode;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String respCode, String message) {
        this.respCode = respCode;
        this.message = message;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
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
