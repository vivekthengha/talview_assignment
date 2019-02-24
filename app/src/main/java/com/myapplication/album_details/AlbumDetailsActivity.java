package com.myapplication.album_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.myapplication.utils.Constants;
import com.myapplication.R;
import com.myapplication.base.BaseActivity;
import com.myapplication.data.model.AlbumDetail;
import com.myapplication.network.FailureResponse;
import com.myapplication.utils.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumDetailsActivity extends BaseActivity implements AlbumDetailsView, AlbumDetailsAdapter.OnAlbumSelectedListener, SwipeRefreshLayout.OnRefreshListener {

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
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private AlbumDetailsPresenter albumDetailsPresenter;
    private AlbumDetailsAdapter albumDetailsAdapter;
    private int albumId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        albumDetailsPresenter = new AlbumDetailsPresenter(this);
        albumId = getIntent().getIntExtra(Constants.IntentConstants.ALBUM_ID, 0);
        showLoadingBar();
        albumDetailsPresenter.fetAlbumDetails(albumId);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        albumDetailsAdapter = new AlbumDetailsAdapter(new ArrayList<AlbumDetail>(), this);
        rvPhotos.setNestedScrollingEnabled(false);
        rvPhotos.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(this, 3);
        rvPhotos.addItemDecoration(new GridSpacingItemDecoration(3, 20, true)); //20 is spacing between each grid items
        rvPhotos.setLayoutManager(linearLayoutManager);
        rvPhotos.setAdapter(albumDetailsAdapter);
    }

    @Override
    public void onAlbumDetailsFetched(List<AlbumDetail> albumDetailsList) {
        albumDetailsAdapter.addAlbumList(albumDetailsList);
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
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showSpecificError(FailureResponse failureResponse) {
        super.showSpecificError(failureResponse);
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
        hideLoadingBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        albumDetailsPresenter.destroy();
    }

    @Override
    public void onAlbumSelected(AlbumDetail albumDetail, View view) {
        Intent intent = new Intent(this, PhotoViewerActivity.class);
        intent.putExtra(Constants.IntentConstants.IMAGE_URL, albumDetail.getUrl());
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "album");
        startActivity(intent, options.toBundle());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        albumDetailsPresenter.fetAlbumDetails(albumId);
    }
}
