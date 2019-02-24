package com.myapplication.album_details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.myapplication.Constants;
import com.myapplication.R;
import com.myapplication.base.BaseActivity;
import com.myapplication.data.model.AlbumDetail;
import com.myapplication.data.model.PostComments;
import com.myapplication.network.FailureResponse;
import com.myapplication.post_details.PostCommentsAdapter;
import com.myapplication.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumDetailsActivity extends BaseActivity implements AlbumDetailsView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.root_view)
    ConstraintLayout rootView;

    private AlbumDetailsPresenter albumDetailsPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        ButterKnife.bind(this);
        albumDetailsPresenter = new AlbumDetailsPresenter(this);
        int albumId = getIntent().getIntExtra(Constants.IntentConstants.ALBUM_ID,0);
        albumDetailsPresenter.fetAlbumDetails(albumId);
    }

    private void setUpRecyclerView() {
   //     postCommentsAdapter = new PostCommentsAdapter(new ArrayList<PostComments>());
        rvPhotos.setNestedScrollingEnabled(false);
        rvPhotos.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this,3);
        rvPhotos.addItemDecoration(new GridSpacingItemDecoration(3,20,true)); //20 is spacing between each grid items
        rvPhotos.setLayoutManager(linearLayoutManager);
      //  rvPhotos.setAdapter(postCommentsAdapter);
    }

    @Override
    public void onAlbumDetailsFetched(List<AlbumDetail> albumDetailsList) {

    }

    @Override
    protected View setSnackView() {
        return rootView;
    }

    @Override
    public void showLoadingBar() {
        pbProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingBar() {
        pbProgress.setVisibility(View.GONE);
    }

    @Override
    public void showSpecificError(FailureResponse failureResponse) {
        super.showSpecificError(failureResponse);
        hideLoadingBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        albumDetailsPresenter.destroy();
    }

}
