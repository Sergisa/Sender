package ru.sergisa.sender;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;


public class SenderApplication extends Application {
    String place = "";

    @Override
    public void onCreate() {

        //APIController.getAPI();
        super.onCreate();
        DataLoader.initInstance();
    }


    public boolean hasConnection(){
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ethInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        Runtime run = Runtime.getRuntime();
        if(ethInfo != null && ethInfo.isConnected()){
            return true;
        }
        ethInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(ethInfo != null && ethInfo.isConnected()){
            return true;
        }
        ethInfo = cm.getActiveNetworkInfo();
        if(ethInfo != null && ethInfo.isConnected()){
            return true;
        }
        try{

            int exitValue = run.exec("/system/bin/ping -c 1 8.8.8.8").waitFor();
            return (exitValue ==0);
        }catch (IOException e){e.printStackTrace();}
        catch (InterruptedException e){e.printStackTrace();}
        return false;

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

    public void toast(String string) {
        Toast.makeText(getApplicationContext(),string,Toast.LENGTH_SHORT).show();
    }
    /*private Enum{

    }*/
}
