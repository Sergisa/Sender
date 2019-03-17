package ru.sergisa.sender;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.sergisa.sender.api.SenderAPI;
import ru.sergisa.sender.models.SenderResponse;
import ru.sergisa.sender.models.SenderResponse.Post;
import ru.sergisa.sender.models.SenderResponse.Type;

public class DataLoader {
    private static DataLoader dataLoaderInstance;
    private DataLoader(){

    }
    public static SenderAPI getAPI(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        Retrofit.Builder builder =
                new Retrofit.Builder()
                        .baseUrl("http://cinema.smrtp.ru/book/public/api/")
                        .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.client(httpClient.build()).build();
        return retrofit.create(SenderAPI.class);


    }
    public static void initInstance(){
        if(dataLoaderInstance == null){
            dataLoaderInstance = new DataLoader();
        }
    }
    public static DataLoader getDataLoaderInstance() {
        return dataLoaderInstance;
    }

    public Post[] getPosts(){
        getAPI().getPosts().enqueue(new Callback<SenderResponse>() {
            @Override
            public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {

            }

            @Override
            public void onFailure(Call<SenderResponse> call, Throwable t) {

            }
        });
        return null;
        //return new Post[new Post()];
    }
    public Type[] getTypes(){
        getAPI().getTypes().enqueue(new Callback<SenderResponse>() {
            @Override
            public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {

            }

            @Override
            public void onFailure(Call<SenderResponse> call, Throwable t) {

            }
        });
        return null;
        //return new Post[new Post()];
    }
    public String getLink(int id){
        getAPI().getLink(id).enqueue(new Callback<SenderResponse>() {
            @Override
            public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {

            }

            @Override
            public void onFailure(Call<SenderResponse> call, Throwable t) {

            }
        });
        return "";
        //return new Post[new Post()];
    }

    public void addPost(Post post){
        getAPI().addPost(post).enqueue(new Callback<SenderResponse>() {
            @Override
            public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {

            }

            @Override
            public void onFailure(Call<SenderResponse> call, Throwable t) {

            }
        });
    }

}
