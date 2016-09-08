package com.hardik.salestask.network.interfaces;

import com.hardik.salestask.network.model.WeResponse;

/**
 * Created by Hardik Shah on 29-07-2015.
 */
public interface Callback<T> {
    void onSuccessResponse(T response);

    void onErrorResponse(WeResponse response);
}
