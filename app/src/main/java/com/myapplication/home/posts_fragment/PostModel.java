package com.myapplication.home.posts_fragment;

import android.annotation.SuppressLint;
import android.util.Log;

import com.myapplication.YasmaApplication;
import com.myapplication.base.BaseModel;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.Post;
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

public class PostModel extends BaseModel<PostModelListener> {
    private static final String TAG = "PostModel";

    private CompositeDisposable compositeDisposable;

    public PostModel(PostModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    public void fetchPosts() {
        compositeDisposable.add(getDataManager().fetchPosts().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new NetworkResponse<List<Post>>(this) {
                    @Override
                    public void onResponse(List<Post> postList) {
                        insertPostsInDb(postList);
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
                        fetchPostFromDb();
                    }
                }));

    }

    private void insertPostsInDb(final List<Post> postList) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                YasmaDatabase.getInstance(YasmaApplication.getInstance()).postDao()
                        .insertPosts(postList);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        fetchPostFromDb();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void fetchPostFromDb() {
        YasmaDatabase.getInstance(YasmaApplication.getInstance()).postDao().getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new MaybeObserver<List<Post>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Post> postList) {
                        getListener().onPostsFetched(postList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

}
