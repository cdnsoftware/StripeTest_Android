package com.test.shop.model;

/**
 * Created by murlidhardaharwal on 27/10/15.
 */
public class ResponseBase {

    private int result_code;
    private String error_msg;
    private String success_msg;

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getSuccess_msg() {
        return success_msg;
    }

    public void setSuccess_msg(String success_msg) {
        this.success_msg = success_msg;
    }
}
