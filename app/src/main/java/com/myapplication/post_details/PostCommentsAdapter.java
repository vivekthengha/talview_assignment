package com.myapplication.post_details;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapplication.R;
import com.myapplication.data.model.PostComments;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostCommentsAdapter extends RecyclerView.Adapter<PostCommentsAdapter.PostCommentsViewHolder> {
    private List<PostComments> postCommentsList;

    public PostCommentsAdapter(List<PostComments> postCommentsList) {
        this.postCommentsList = postCommentsList;
    }

    @NonNull
    @Override
    public PostCommentsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_comments, viewGroup, false);
        return new PostCommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCommentsViewHolder postCommentsViewHolder, int i) {
        postCommentsViewHolder.bind(postCommentsList.get(i));
    }

    @Override
    public int getItemCount() {
        return postCommentsList.size();
    }

    class PostCommentsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_body)
        TextView tvBody;
        PostCommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void bind(PostComments postComments) {
            tvName.setText(postComments.getName());
            tvBody.setText(postComments.getBody());
        }
    }

}
