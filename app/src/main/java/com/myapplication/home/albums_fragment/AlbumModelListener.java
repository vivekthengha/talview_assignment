package com.myapplication.home.albums_fragment;

import com.myapplication.base.BaseModelListener;
import com.myapplication.data.model.Album;

import java.util.List;

public interface AlbumModelListener extends BaseModelListener {
    void onAlbumsFetched(List<Album> postList);
}
