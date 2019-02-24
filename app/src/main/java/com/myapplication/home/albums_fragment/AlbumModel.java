package com.myapplication.home.albums_fragment;

import android.util.Log;

import com.myapplication.base.BaseModel;
import com.myapplication.data.model.Album;
import com.myapplication.data.model.Post;
import com.myapplication.network.FailureResponse;
import com.myapplication.network.NetworkResponse;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumModel extends BaseModel<AlbumModelListener>{
    private static final String TAG = "AlbumModel";

    private CompositeDisposable compositeDisposable;

    public AlbumModel(AlbumModelListener listener) {
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

    public void fetchPosts(){
        compositeDisposable.add(getDataManager().fetchAlbums().subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                        .subscribeWith(new NetworkResponse<List<Album>>(this) {
                                            @Override
                                            public void onResponse(List<Album> postList) {
                                                getListener().onAlbumsFetched(postList);
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
                                        }));

    }

}
