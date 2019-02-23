package com.myapplication.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.myapplication.data.model.Album;
import com.myapplication.data.model.AlbumDetail;
import com.myapplication.data.model.Post;

/**
 * The Room database that contains the Posts table and Albums table
 */

@Database(entities = {Post.class, Album.class, AlbumDetail.class}, version = 1)
public abstract class YasmaDatabase extends RoomDatabase {

    private static volatile YasmaDatabase INSTANCE;

    public abstract PostDao postDao();

    public static RoomDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    YasmaDatabase.class, "yasma.db")
                                        .build();
                }
            }
        }
        return INSTANCE;
    }

}