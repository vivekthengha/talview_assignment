package com.myapplication.home.posts_fragment;

import com.myapplication.base.BaseModelListener;
import com.myapplication.data.model.Post;

import java.util.List;

public interface PostModelListener extends BaseModelListener {
    void onPostsFetched(List<Post> postList);
}
