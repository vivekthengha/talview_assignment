package com.myapplication.data;

import com.myapplication.data.api.ApiManager;
import com.myapplication.data.model.Album;
import com.myapplication.data.model.AlbumDetail;
import com.myapplication.data.model.Post;
import com.myapplication.data.model.PostComments;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;

/**
 * Created by vivek jha on 22/2/19.
 */

public class DataManager implements IDataManager {

    private ApiManager apiManager;
    private static DataManager instance;

    private DataManager() {
        apiManager = ApiManager.getInstance();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            synchronized (DataManager.class){
                    instance = new DataManager();
            }
        }
        return instance;
    }

    @Override
    public Single<Response<List<Post>>> fetchPosts() {
        return apiManager.fetchPosts();
    }

    @Override
    public Single<Response<List<PostComments>>> fetchComments(int postId) {
        return apiManager.fetchComments(postId);
    }

    @Override
    public Single<Response<List<Album>>> fetchAlbums() {
        return apiManager.fetchAlbums();
    }

    @Override
    public Single<Response<List<AlbumDetail>>> fetchAlbumDetails(int albumId) {
        return apiManager.fetchAlbumDetails(albumId);
    }
}
