package com.myapplication.base;

import com.myapplication.network.FailureResponse;

/**
 * Created by vivek jha on 22/2/19.
 */

public interface BaseView {
    void showNoNetworkError();
    void showToastLong(String message);
    void showSpecificError(FailureResponse failureResponse);
    void showProgressDialog();
    void hideProgressDialog();
}
