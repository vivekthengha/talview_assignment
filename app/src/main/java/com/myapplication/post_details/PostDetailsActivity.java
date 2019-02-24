package com.myapplication.post_details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.myapplication.Constants;
import com.myapplication.R;
import com.myapplication.YasmaApplication;
import com.myapplication.base.BaseActivity;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.Post;
import com.myapplication.data.model.PostComments;
import com.myapplication.network.FailureResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostDetailsActivity extends BaseActivity implements PostDetailsView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_body)
    TextView tvBody;
    @BindView(R.id.rv_comments)
    RecyclerView rvComments;
    @BindView(R.id.nested_scroll_view)
    NestedScrollView nestedScrollView;
    @BindView(R.id.root_view)
    CoordinatorLayout rootView;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgressbar;

    private PostDetailsPresenter postDetailsPresenter;
    private PostCommentsAdapter postCommentsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        postDetailsPresenter = new PostDetailsPresenter(this);

        Post post = getIntent().getParcelableExtra(Constants.IntentConstants.POST);
        postDetailsPresenter.fetchComments(post.getId());

        setData(post);

        setUpRecyclerView();
    }

    private void setData(Post post) {
        tvBody.setText(post.getBody());
        tvTitle.setText(post.getTitle());
    }

    private void setUpRecyclerView() {
        postCommentsAdapter = new PostCommentsAdapter(new ArrayList<PostComments>());
        rvComments.setNestedScrollingEnabled(false);
        rvComments.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvComments.addItemDecoration(new DividerItemDecoration(this,linearLayoutManager.getOrientation()));
        rvComments.setLayoutManager(linearLayoutManager);
        rvComments.setAdapter(postCommentsAdapter);
    }

    @Override
    protected View setSnackView() {
        return rootView;
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
    public void onPostCommentsFetched(List<PostComments> postCommentsList) {
        hideLoadingBar();
        postCommentsAdapter.addComments(postCommentsList);
    }

    @Override
    public void showLoadingBar() {
        pbProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingBar() {
        pbProgressbar.setVisibility(View.GONE);
    }

    @Override
    public void showSpecificError(FailureResponse failureResponse) {
        super.showSpecificError(failureResponse);
        hideLoadingBar();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        postDetailsPresenter.destroy();
    }
}
