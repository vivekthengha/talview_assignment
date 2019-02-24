package com.myapplication.post_details;

import com.myapplication.base.BaseView;
import com.myapplication.data.model.PostComments;

import java.util.List;

public interface PostDetailsView extends BaseView {
    void onPostCommentsFetched(List<PostComments> postCommentsList);
}
