package ru.sergisa.sender;

import android.app.Application;
import android.content.res.Configuration;
import android.widget.Toast;

public class SenderApplication extends Application {
    String place = "";
    @Override
    public void onCreate() {
        //APIController.getAPI();
        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Toast.makeText(getApplicationContext(),"Changed "+place,Toast.LENGTH_LONG).show();
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
