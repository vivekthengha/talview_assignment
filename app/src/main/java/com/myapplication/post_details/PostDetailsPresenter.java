package com.myapplication.post_details;

import com.myapplication.YasmaApplication;
import com.myapplication.base.BasePresenter;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.PostComments;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class PostDetailsPresenter extends BasePresenter<PostDetailsView> implements PostDetailsModelListener{

    private PostDetailsModel postDetailsModel;
    private Disposable disposable;

    PostDetailsPresenter(PostDetailsView view) {
        super(view);
    }

    @Override
    protected void setModel() {
        postDetailsModel = new PostDetailsModel(this);
        postDetailsModel.init();
    }

    @Override
    protected void destroy() {
        postDetailsModel.detachListener();
        postDetailsModel.dispose();
        if (disposable != null)
            disposable.dispose();
        postDetailsModel = null;
    }

    @Override
    public void onPostCommentsFetched(final List<PostComments> postCommentsList) {
        getView().hideLoadingBar();
       getView().onPostCommentsFetched(postCommentsList);
    }

    void fetchComments(Integer postId) {
        postDetailsModel.fetchComments(postId);
    }

    @Override
    public void noNetworkError() {
        super.noNetworkError();
        getView().hideLoadingBar();
    }
}
