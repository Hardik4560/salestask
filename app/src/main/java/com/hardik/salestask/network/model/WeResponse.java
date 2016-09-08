
package com.hardik.salestask.network.model;

import org.json.JSONObject;

/**
 * Created by Hardik Shah on 10-07-2015.
 */
public class WeResponse {
    private String status;
    private int status_code;

    private WeData data;

    JSONObject successResponse; //For internal purpose for sending the response to the user.

    int error_code;
    String message;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public WeData getData() {
        return data;
    }

    public void setData(WeData data) {
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getSuccessResponse() {
        return successResponse;
    }

    public void setSuccessResponse(JSONObject successResponse) {
        this.successResponse = successResponse;
    }
}
