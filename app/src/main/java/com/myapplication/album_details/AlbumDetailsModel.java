package com.myapplication.album_details;

import android.annotation.SuppressLint;
import android.util.Log;

import com.myapplication.YasmaApplication;
import com.myapplication.base.BaseModel;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.AlbumDetail;
import com.myapplication.data.model.PostComments;
import com.myapplication.network.FailureResponse;
import com.myapplication.network.NetworkResponse;
import com.myapplication.post_details.PostDetailsModelListener;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class AlbumDetailsModel extends BaseModel<AlbumDetailsModelListener> {

    private static final String TAG = "PostDetailsModel";

    private CompositeDisposable compositeDisposable;

    public AlbumDetailsModel(AlbumDetailsModelListener listener) {
        super(listener);
    }

    @Override
    public void init() {
        compositeDisposable = new CompositeDisposable();
    }

    @SuppressLint("CheckResult")
    public void fetchAlbumDetails(final int albumId) {
        compositeDisposable.add(getDataManager().fetchAlbumDetails(albumId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new NetworkResponse<List<AlbumDetail>>(this) {
                    @Override
                    public void onResponse(List<AlbumDetail> albumDetailList) {
                        insertIntoDb(albumDetailList, albumId);
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
                        fetchCommentsFromDatabase(albumId);
                    }
                }));
    }

    @Override
    public void dispose() {
        compositeDisposable.clear();
    }

    public void insertIntoDb(final List<AlbumDetail> albumDetailList, final int albumId) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                YasmaDatabase.getInstance(YasmaApplication.getInstance()).albumDao()
                        .insertAlbumDetails(albumDetailList);
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
                        fetchCommentsFromDatabase(albumId);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    void fetchCommentsFromDatabase(int albumId) {
        YasmaDatabase.getInstance(YasmaApplication.getInstance()).albumDao().getAlbumDetails(albumId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MaybeObserver<List<AlbumDetail>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if (d != null)
                            compositeDisposable.add(d);
                    }

                    @Override
                    public void onSuccess(List<AlbumDetail> albumDetailList) {
                        getListener().onAlbumDetailsFetched(albumDetailList);
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
