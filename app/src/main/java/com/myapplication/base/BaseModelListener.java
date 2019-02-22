package com.myapplication.base;

import com.myapplication.network.FailureResponse;

/**
 * Created by vivek jha on 22/2/19.
 */

public interface BaseModelListener {
    void noNetworkError();
    void onErrorOccurred(FailureResponse failureResponse);
}