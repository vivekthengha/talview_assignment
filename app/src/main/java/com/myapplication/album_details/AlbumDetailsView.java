package com.myapplication.album_details;

import com.myapplication.base.BaseView;
import com.myapplication.data.model.AlbumDetail;

import java.util.List;

public interface AlbumDetailsView extends BaseView {
    void onAlbumDetailsFetched(List<AlbumDetail> albumDetailsList);
}
