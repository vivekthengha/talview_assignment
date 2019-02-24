package com.myapplication.post_details;

import android.annotation.SuppressLint;
import android.util.Log;

import com.myapplication.base.BaseModel;
import com.myapplication.data.model.PostComments;
import com.myapplication.network.FailureResponse;
import com.myapplication.network.NetworkResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class PostDetailsModel extends BaseModel<PostDetailsModelListener> {

    private static final String TAG = "PostDetailsModel";

    CompositeDisposable compositeDisposable;

    public PostDetailsModel(PostDetailsModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {
        compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    public void fetchComments(int postId){
        getDataManager().fetchComments(postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new NetworkResponse<List<PostComments>>(this) {
                        @Override
                        public void onResponse(List<PostComments> postCommentsList) {
                            getListener().onPostCommentsFetched(postCommentsList);
                        }

                        @Override
                        public void onFailure(int code, FailureResponse failureResponse) {
                            Log.d(TAG, failureResponse.getMsg());
                            getListener().onErrorOccurred(failureResponse);
                        }

                        @Override
                        public void onSpecificError(Throwable t) {
                            Log.d(TAG, "Error");
                        }
                    });
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }
}
