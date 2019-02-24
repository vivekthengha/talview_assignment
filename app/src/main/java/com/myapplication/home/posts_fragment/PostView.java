package com.myapplication.home.posts_fragment;

import com.myapplication.base.BaseView;
import com.myapplication.data.model.Post;

import java.util.List;

public interface PostView extends BaseView {
    void onPostsFetched(List<Post> postList);
}
