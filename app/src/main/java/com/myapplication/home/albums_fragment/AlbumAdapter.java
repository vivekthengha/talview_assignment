package com.myapplication.home.albums_fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myapplication.R;
import com.myapplication.data.model.Album;
import com.myapplication.data.model.Post;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.PostViewHolder> {
    private List<Album> albumList;
    private AlbumItemSelectedListener listener;

    public AlbumAdapter(List<Album> albumList, AlbumItemSelectedListener listener) {
        this.albumList = albumList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_posts, viewGroup, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        postViewHolder.bind(albumList.get(i));
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_body)
        TextView tvBody;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        void bind(Album album) {
            tvTitle.setText(String.format(Locale.getDefault(),"Album:- %d", album.getId()));
            tvBody.setText(album.getTitle());
        }

        @Override
        public void onClick(View view) {
            listener.onAlbumSelected(albumList.get(getAdapterPosition()));
        }
    }

    public interface AlbumItemSelectedListener {
        void onAlbumSelected(Album album);
    }

}
