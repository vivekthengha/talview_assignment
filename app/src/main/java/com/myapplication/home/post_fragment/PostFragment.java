package com.myapplication.home.post_fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.myapplication.R;
import com.myapplication.YasmaApplication;
import com.myapplication.base.BaseFragment;
import com.myapplication.data.db.YasmaDatabase;
import com.myapplication.data.model.Post;
import com.myapplication.home.HomeActivity;
import com.myapplication.network.FailureResponse;

import org.reactivestreams.Subscription;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.FlowableSubscriber;
import io.reactivex.MaybeObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PostFragment extends BaseFragment implements PostView {

    @BindView(R.id.rv_posts)
    RecyclerView rvPosts;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    Unbinder unbinder;

    private PostFragmentInteractionListener listener;

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
        return view;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onPostsFetched(List<Post> postList) {
        YasmaDatabase.getInstance(YasmaApplication.getInstance()).postDao().getPosts()
                .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new MaybeObserver<List<Post>>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(List<Post> postList) {

                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {

                            }
                        });
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
    }

    public interface PostFragmentInteractionListener {
        void showSnackBar(String message);
    }

}
