package com.myapplication.network;

/**
 * Created by vivek jha on 22/2/19.
 */

/**
 * This is to be used for handling common responses
 * such as no network or authentication failed
 * */

public interface CommonResponseHandler {
    void onNetworkError();
}
