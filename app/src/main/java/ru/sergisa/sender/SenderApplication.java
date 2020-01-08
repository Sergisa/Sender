package ru.sergisa.sender;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.sergisa.sender.api.SenderAPI;
import ru.sergisa.sender.utils.InternetConnectionListener;
import ru.sergisa.sender.utils.NetworkConnectionInterceptor;


public class SenderApplication extends Application {
    private SenderAPI apiService;
    private InternetConnectionListener mInternetConnectionListener;

    String place = "";

    @Override
    public void onCreate() {

        //APIController.getAPI();
        super.onCreate();

    }

    public void setInternetConnectionListener(InternetConnectionListener listener) {
        mInternetConnectionListener = listener;
    }

    public void removeInternetConnectionListener() {
        mInternetConnectionListener = null;
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public SenderAPI getApiService() {
        if (apiService == null) {
            apiService = provideRetrofit().create(SenderAPI.class);
        }
        return apiService;
    }

    private Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(SenderAPI.URL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
    }

    private OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okhttpClientBuilder = new OkHttpClient.Builder();
        okhttpClientBuilder.connectTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.readTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS);
        okhttpClientBuilder.addInterceptor(new NetworkConnectionInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return SenderApplication.this.isInternetAvailable();
            }

            @Override
            public void onInternetUnavailable() {
                if (mInternetConnectionListener != null) {
                    mInternetConnectionListener.onInternetUnavailable();
                }
            }
        });
        return okhttpClientBuilder.build();
    }

}
