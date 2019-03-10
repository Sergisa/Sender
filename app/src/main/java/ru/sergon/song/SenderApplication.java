package ru.sergon.song;

import android.app.Application;

import ru.sergon.song.api.APIController;

public class SenderApplication extends Application {
    @Override
    public void onCreate() {
        //APIController.getAPI();
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public void getPosts(){

    }

}
