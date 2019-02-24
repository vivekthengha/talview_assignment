package com.myapplication.home.albums_fragment;

import android.annotation.SuppressLint;
import android.util.Log;

import com.myapplication.YasmaApplication;
import com.myapplication.base.BaseModel;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.Album;
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

public class AlbumModel extends BaseModel<AlbumModelListener> {
    private static final String TAG = "AlbumModel";

    private CompositeDisposable compositeDisposable;

    AlbumModel(AlbumModelListener listener) {
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

    public void fetchAlbums() {
        compositeDisposable.add(getDataManager().fetchAlbums().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new NetworkResponse<List<Album>>(this) {
                    @Override
                    public void onResponse(List<Album> postList) {
                        insertAlbumsInDb(postList);
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
                        fetchAlbumsFromDb();
                    }
                }));

    }

    private void insertAlbumsInDb(final List<Album> albumList) {
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
                        if (d != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onComplete() {
                        fetchAlbumsFromDb();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void fetchAlbumsFromDb() {
        YasmaDatabase.getInstance(YasmaApplication.getInstance()).albumDao().getAlbums()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new MaybeObserver<List<Album>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<Album> albumList) {
                        getListener().onAlbumsFetched(albumList);
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
