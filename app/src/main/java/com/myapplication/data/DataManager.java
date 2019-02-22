package com.myapplication.data;



import com.myapplication.data.api.ApiManager;

import retrofit2.Call;

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

}
