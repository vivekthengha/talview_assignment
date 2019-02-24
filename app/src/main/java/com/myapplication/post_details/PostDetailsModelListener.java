package com.myapplication.post_details;

import com.myapplication.base.BaseModelListener;
import com.myapplication.data.model.PostComments;

import java.util.List;

public interface PostDetailsModelListener extends BaseModelListener {
    void onPostCommentsFetched(List<PostComments> postCommentsList);
}
