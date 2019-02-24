package com.myapplication.post_details;

import android.annotation.SuppressLint;
import android.util.Log;

import com.myapplication.YasmaApplication;
import com.myapplication.base.BaseModel;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.PostComments;
import com.myapplication.network.FailureResponse;
import com.myapplication.network.NetworkResponse;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class PostDetailsModel extends BaseModel<PostDetailsModelListener> {

    private static final String TAG = "PostDetailsModel";

    private CompositeDisposable compositeDisposable;

    public PostDetailsModel(PostDetailsModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {
        compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    public void fetchComments(final int postId) {
        compositeDisposable.add(getDataManager().fetchComments(postId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new NetworkResponse<List<PostComments>>(this) {
                    @Override
                    public void onResponse(List<PostComments> postCommentsList) {
                        insertIntoDb(postCommentsList, postId);
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

                    @Override
                    public void onNetworkError() {
                        this.dispose();
                        fetchCommentsFromDatabase(postId);
                    }
                }));
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    public void insertIntoDb(final List<PostComments> postCommentsList, final int postId) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                YasmaDatabase.getInstance(YasmaApplication.getInstance()).postDao()
                        .insertPostComments(postCommentsList);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        fetchCommentsFromDatabase(postId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void fetchCommentsFromDatabase(int postId) {
        YasmaDatabase.getInstance(YasmaApplication.getInstance()).postDao().getPostComments(postId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<PostComments>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<PostComments> postCommentsList) {
                        getListener().onPostCommentsFetched(postCommentsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("No", "no network");
                    }

                    @Override
                    public void onComplete() {
                        Log.d("No", "complete");
                    }
                });
    }

}
