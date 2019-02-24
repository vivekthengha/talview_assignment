package com.myapplication.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.myapplication.data.model.Album;
import com.myapplication.data.model.AlbumDetail;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Data access object class to be used for accessing data from database.
 */

@Dao
public interface AlbumDao {

    /**
     * Insert albums list in the table. If the album already exists, replace it.
     *
     * @param albums the albums to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbums(List<Album> albums);

    /**
     * Get the albums from the table. This query gets all users from the table.
     * Flowable is used to emit data whenever there is change in data
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM Albums")
    Maybe<List<Album>> getAlbums();

    /**
     * Insert album details list in the database. If the album already exists, replace it.
     *
     * @param albumDetails the details to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAlbumDetails(List<AlbumDetail> albumDetails);

    /**
     * Get the album details list from the table. This query gets all the photos list from the table.
     * Flowable is used to emit data whenever there is change in data
     *
     * @return the user from the table
     */
    @Query("SELECT * FROM AlbumDetails")
    Maybe<List<AlbumDetail>> getAlbumDetails();

}
