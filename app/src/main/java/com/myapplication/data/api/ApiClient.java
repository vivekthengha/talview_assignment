package com.myapplication.data.api;

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
import retrofit2.http.Query;

interface ApiClient {

    /**
     * api to fetch posts from server
     * @return will return list of posts in single emission
     */
    @GET("posts")
    Single<Response<List<Post>>> fetchPosts();

     /**
     * api to fetch posts from server
     * @return will return list of posts in single emission
     */
    @GET("posts/{postId}/comments")
    Single<List<PostComments>> fetchComments(@Path("post_id") int postId);

    /**
     * api to fetch albums from server
     * @return will return list of albums in single emission
     */
    @GET("albums")
    Single<Response<List<Album>>> fetchAlbums();

     /**
     * api to fetch albums from server
     * @return will return list of albums in single emission
     */
    @GET("albums/{albumId}/photos")
    Single<List<AlbumDetail>> fetchAlbumDetails(@Path("album_id") int albumId);

}
