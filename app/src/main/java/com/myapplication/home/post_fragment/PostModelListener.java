package com.myapplication.home.post_fragment;

import android.support.v7.view.menu.BaseMenuPresenter;

import com.myapplication.base.BaseModelListener;
import com.myapplication.data.model.Post;

import java.util.List;

public interface PostModelListener extends BaseModelListener {
    void onPostsFetched(List<Post> postList);
}
