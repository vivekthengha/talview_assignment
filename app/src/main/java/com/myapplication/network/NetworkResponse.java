package com.myapplication.network;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by vivek jha on 22/2/19.
 */

public abstract class NetworkResponse<T> extends DisposableSingleObserver<Response<T>> {
    private CommonResponseHandler handler;

    public NetworkResponse(CommonResponseHandler handler){
        this.handler = handler;
    }

    public abstract void onResponse(T body);
    public abstract void onFailure(int code, FailureResponse failureResponse);
    public abstract void onSpecificError(Throwable t);

    protected void failure(int code, FailureResponse failureResponse){
        onFailure(code, failureResponse);
    }

    protected void error(Throwable t){
        if(t instanceof SocketTimeoutException || t instanceof UnknownHostException){
            handler.onNetworkError();
        }
        onSpecificError(t);
    }

    @Override
    public void onSuccess(Response<T> response){
        if (response.code() == 200 && response.isSuccessful()){
            onResponse(response.body());
        }else {
            try {
                assert response.errorBody() != null;
                failure(response.code(),getFailureErrorBody(response.errorBody().string(),response));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable t) {
        error(t);
    }

    /**
     * Create your custom failure response out of server response
     * Also save Url for any further use
     * */
    protected final FailureResponse getFailureErrorBody(String errorStr, Response<T> errorBody) {
        FailureResponse failureResponse = new FailureResponse();
        failureResponse.setMsg(errorBody.message());
        failureResponse.setErrorCode(errorBody.code());
        return failureResponse;
    }

}
