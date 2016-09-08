package com.hardik.salestask.network.interfaces;

import com.hardik.salestask.network.model.WeResponse;

/**
 * Created by Hardik Shah on 03-08-2015.
 */
public abstract class OnCompleteListener<T> {
    public abstract void onComplete(T responseData);

    public void onError(WeResponse responseData) {
    }
}
