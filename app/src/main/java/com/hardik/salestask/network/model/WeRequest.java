
package com.hardik.salestask.network.model;

/**
 * Created by Hardik Shah on 15-07-2015.
 */
public class WeRequest {

    String api;
    String api_key;

    WeData data;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public WeData getData() {
        return data;
    }

    public void setData(WeData data) {
        this.data = data;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
}
