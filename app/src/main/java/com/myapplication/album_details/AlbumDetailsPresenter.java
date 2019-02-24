package com.myapplication.album_details;

import com.myapplication.base.BasePresenter;
import com.myapplication.data.model.AlbumDetail;

import java.util.List;

public class AlbumDetailsPresenter extends BasePresenter<AlbumDetailsView> implements AlbumDetailsModelListener{

    private AlbumDetailsModel albumDetailsModel;

    AlbumDetailsPresenter(AlbumDetailsView view) {
        super(view);
    }

    @Override
    protected void setModel() {
        albumDetailsModel = new AlbumDetailsModel(this);
        albumDetailsModel.init();
    }

    @Override
    protected void destroy() {
        albumDetailsModel.detachListener();
        albumDetailsModel.dispose();
        albumDetailsModel = null;
    }

    void fetAlbumDetails(Integer albumId) {
        getView().showLoadingBar();
        albumDetailsModel.fetchAlbumDetails(albumId);
    }

    @Override
    public void noNetworkError() {
        super.noNetworkError();
        getView().hideLoadingBar();
    }

    @Override
    public void onAlbumDetailsFetched(List<AlbumDetail> albumDetailList) {
        getView().hideLoadingBar();
        getView().onAlbumDetailsFetched(albumDetailList);
    }

}
