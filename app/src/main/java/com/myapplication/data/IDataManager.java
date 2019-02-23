package com.myapplication.data;

import com.myapplication.data.model.Album;
import com.myapplication.data.model.AlbumDetail;
import com.myapplication.data.model.Post;
import com.myapplication.data.model.PostComments;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by vivek jha on 22/2/19
 */

interface IDataManager {
    Single<Response<List<Post>>> fetchPosts();
    Single<List<PostComments>> fetchComments(int postId);
    Single<Response<List<Album>>> fetchAlbums();
    Single<List<AlbumDetail>> fetchAlbumDetails(int albumId);
}
