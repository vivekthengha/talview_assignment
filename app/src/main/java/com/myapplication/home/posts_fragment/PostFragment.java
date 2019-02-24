package com.myapplication.home.posts_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.myapplication.Constants;
import com.myapplication.R;
import com.myapplication.YasmaApplication;
import com.myapplication.base.BaseFragment;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.Post;
import com.myapplication.home.HomeActivity;
import com.myapplication.network.FailureResponse;
import com.myapplication.post_details.PostDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostFragment extends Fragment implements PostView, PostAdapter.PostItemSelectedListener {

    @BindView(R.id.rv_posts)
    RecyclerView rvPosts;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    Unbinder unbinder;

    private PostFragmentInteractionListener listener;
    private PostPresenter postPresenter;
    private PostAdapter postAdapter;
    private List<Post> postList = new ArrayList<>();

    public static PostFragment getInstance() {
        PostFragment postFragment = new PostFragment();
        return postFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof HomeActivity) {
            listener = (PostFragmentInteractionListener) context;
        } else
            throw new IllegalStateException("Home must implement PostFragmentInteractionListener in order to communicate with activity");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpRecyclerView();
        postPresenter = new PostPresenter(this);
        postPresenter.fetchPosts();
        return view;
    }

    private void setUpRecyclerView() {
        postAdapter = new PostAdapter(postList, this);
        rvPosts.setNestedScrollingEnabled(true);
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setHasFixedSize(true);
        rvPosts.setAdapter(postAdapter);
    }

    @Override
    public void onPostsFetched(List<Post> postList) {
        PostFragment.this.postList.clear();
        PostFragment.this.postList.addAll(postList);
        postAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoNetworkError() {
        hideLoadingBar();
    }

    @Override
    public void showSnackbarLong(String message) {
        hideLoadingBar();
        listener.showSnackBar(message);
    }

    @Override
    public void showSpecificError(FailureResponse failureResponse) {
        hideLoadingBar();
        listener.showSnackBar(failureResponse.getMsg());
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        postPresenter.destroy();
    }

    @Override
    public void onPostSelected(Post post) {
        Intent postDetailsIntent = new Intent(getContext(), PostDetailsActivity.class);
        postDetailsIntent.putExtra(Constants.IntentConstants.POST,post);
        startActivity(postDetailsIntent);
    }

    public interface PostFragmentInteractionListener {
        void showSnackBar(String message);
    }

}
