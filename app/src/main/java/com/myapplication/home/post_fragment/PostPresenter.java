package com.myapplication.home.post_fragment;

import android.annotation.SuppressLint;

import com.myapplication.YasmaApplication;
import com.myapplication.base.BasePresenter;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.Post;

import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostPresenter extends BasePresenter<PostView> implements PostModelListener {

    private PostModel postModel;
    private Disposable disposable;

    public PostPresenter(PostView view) {
        super(view);
    }

    @Override
    protected void setModel() {
        postModel = new PostModel(this);
    }

    @Override
    protected void destroy() {
        postModel.detachListener();
        postModel.dispose();
        if (disposable != null)
            disposable.dispose();
        postModel = null;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onPostsFetched(final List<Post> postList) {
        YasmaDatabase.getInstance(YasmaApplication.getInstance()).postDao()
                .insertPosts(postList).subscribeOn(Schedulers.io())
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        getView().onPostsFetched(postList);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

}
