package com.myapplication.album_details;

import com.myapplication.base.BasePresenter;
import com.myapplication.data.model.AlbumDetail;
import com.myapplication.data.model.PostComments;
import com.myapplication.post_details.PostDetailsModel;
import com.myapplication.post_details.PostDetailsModelListener;
import com.myapplication.post_details.PostDetailsView;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class AlbumDetailsPresenter extends BasePresenter<AlbumDetailsView> implements AlbumDetailsModelListener{

    private AlbumDetailsModel postDetailsModel;

    AlbumDetailsPresenter(AlbumDetailsView view) {
        super(view);
    }

    @Override
    protected void setModel() {
        postDetailsModel = new AlbumDetailsModel(this);
        postDetailsModel.init();
    }

    @Override
    protected void destroy() {
        postDetailsModel.detachListener();
        postDetailsModel.dispose();
        postDetailsModel = null;
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

    @Override
    public void onAlbumDetailsFetched(List<AlbumDetail> albumDetailList) {
        getView().onAlbumDetailsFetched(albumDetailList);
    }

}
