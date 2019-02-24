package com.myapplication.album_details;

import com.myapplication.base.BasePresenter;
import com.myapplication.data.model.PostComments;
import com.myapplication.post_details.PostDetailsModel;
import com.myapplication.post_details.PostDetailsModelListener;
import com.myapplication.post_details.PostDetailsView;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class AlbumDetailsPresenter extends BasePresenter<AlbumDetailsView> implements AlbumDetailsView{

    private PostDetailsModel postDetailsModel;
    private Disposable disposable;

    AlbumDetailsPresenter(PostDetailsView view) {
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
       getView().onPostCommentsFetched(postCommentsList);
    }

    void fetchComments(Integer postId) {
        getView().showLoadingBar();
        postDetailsModel.fetchComments(postId);
    }

    @Override
    public void noNetworkError() {
        super.noNetworkError();
        getView().hideLoadingBar();
    }
}
