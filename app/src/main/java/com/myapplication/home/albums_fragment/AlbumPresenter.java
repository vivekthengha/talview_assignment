package com.myapplication.home.albums_fragment;

import android.annotation.SuppressLint;

import com.myapplication.YasmaApplication;
import com.myapplication.base.BasePresenter;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.Album;
import com.myapplication.data.model.Post;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AlbumPresenter extends BasePresenter<AlbumView> implements AlbumModelListener {

    private AlbumModel albumModel;
    private Disposable disposable;

    public AlbumPresenter(AlbumView view) {
        super(view);
    }

    @Override
    protected void setModel() {
        albumModel = new AlbumModel(this);
        albumModel.init();
    }

    @Override
    protected void destroy() {
        albumModel.detachListener();
        albumModel.dispose();
        if (disposable != null)
            disposable.dispose();
        albumModel = null;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onAlbumsFetched(final List<Album> albumList) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                YasmaDatabase.getInstance(YasmaApplication.getInstance()).albumDao()
                        .insertAlbums(albumList);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        getView().hideLoadingBar();
                        getView().onAlbumsFetched(albumList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().hideLoadingBar();
                    }
                });
    }

    public void fetchPosts() {
        getView().showLoadingBar();
        albumModel.fetchPosts();
    }

}
