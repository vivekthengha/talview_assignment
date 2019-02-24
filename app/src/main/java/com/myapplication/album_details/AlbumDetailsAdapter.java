package com.myapplication.album_details;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.myapplication.R;
import com.myapplication.data.model.AlbumDetail;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlbumDetailsAdapter extends RecyclerView.Adapter<AlbumDetailsAdapter.AlbumDetailsViewHolder> {

    private List<AlbumDetail> albumDetailsList;
    private OnAlbumSelectedListener onAlbumSelectedListener;

    public AlbumDetailsAdapter(List<AlbumDetail> albumDetailsList, OnAlbumSelectedListener onAlbumSelectedListener) {
        this.albumDetailsList = albumDetailsList;
        this.onAlbumSelectedListener = onAlbumSelectedListener;
    }

    @NonNull
    @Override
    public AlbumDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_album, viewGroup, false);
        return new AlbumDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumDetailsViewHolder albumDetailsViewHolder, int i) {
        albumDetailsViewHolder.bind(albumDetailsList.get(i));
    }

    @Override
    public int getItemCount() {
        return albumDetailsList.size();
    }

    public void addAlbumList(List<AlbumDetail> albumDetailsList) {
        this.albumDetailsList.clear();
        this.albumDetailsList.addAll(albumDetailsList);
        notifyDataSetChanged();
    }

    class AlbumDetailsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.iv_album)
        ImageView ivAlbum;
        @BindView(R.id.pb_album)
        ProgressBar pbAlbum;
        AlbumDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onAlbumSelectedListener.onAlbumSelected(albumDetailsList.get(getAdapterPosition()),ivAlbum);
        }

        public void bind(AlbumDetail albumDetail) {
            Glide.with(ivAlbum.getContext()).load(albumDetail.getUrl())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbAlbum.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbAlbum.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(ivAlbum);
        }
    }

    public interface OnAlbumSelectedListener {
        void onAlbumSelected(AlbumDetail albumDetail, View view);
    }

}
