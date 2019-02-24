package com.myapplication.album_details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.myapplication.utils.Constants;
import com.myapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoViewerActivity extends AppCompatActivity {

    @BindView(R.id.iv_album)
    ImageView ivAlbum;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        ButterKnife.bind(this);
        Glide.with(ivAlbum.getContext()).load(getIntent().getStringExtra(Constants.IntentConstants.IMAGE_URL))
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(new RequestOptions().centerCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(ivAlbum);
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        supportFinishAfterTransition();
    }
}
