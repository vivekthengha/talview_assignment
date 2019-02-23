package com.myapplication.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.myapplication.data.model.Album;
import com.myapplication.data.model.Post;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

/**
 * Data access object class to be used for accessing data from database.
 */

@Dao
public interface PostDao {

    /**
     * Insert posts list in the database. If the post already exists, replace it.
     *
     * @param posts the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPosts(List<Post> posts);

    /**
     * Get the album posts list from the table. This query gets all the posts list from the table.
     * Flowable is used to emit data whenever there is change in data
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM Albums")
    Flowable<List<Post>> getPosts();

}
