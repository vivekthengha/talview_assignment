package com.myapplication.home.posts_fragment;

import android.annotation.SuppressLint;

import com.myapplication.R;
import com.myapplication.YasmaApplication;
import com.myapplication.base.BasePresenter;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.Post;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
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
        postModel.init();
    }

    @Override
    protected void destroy() {
        postModel.detachListener();
        postModel.dispose();
        if (disposable != null)
            disposable.dispose();
        postModel = null;
    }

    @Override
    public void onPostsFetched(List<Post> postList) {
        getView().hideLoadingBar();
       getView().onPostsFetched(postList);
    }

    public void fetchPosts() {
        postModel.fetchPosts();
    }

    @Override
    public void noNetworkError() {
        super.noNetworkError();
        getView().showSnackbarLong(YasmaApplication.getInstance().getString(R.string.no_network_error));
    }
}
