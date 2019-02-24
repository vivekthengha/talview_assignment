package com.myapplication.home.albums_fragment;

import com.myapplication.base.BaseView;
import com.myapplication.data.model.Album;

import java.util.List;

public interface AlbumView extends BaseView {
    void onAlbumsFetched(List<Album> albumList);
}
