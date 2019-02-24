package com.myapplication.album_details;

import com.myapplication.base.BaseModelListener;
import com.myapplication.data.model.AlbumDetail;

import java.util.List;

public interface AlbumDetailsModelListener extends BaseModelListener {
    void onAlbumDetailsFetched(List<AlbumDetail> albumDetailList);
}
