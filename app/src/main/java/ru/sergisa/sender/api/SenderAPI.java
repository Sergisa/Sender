package ru.sergisa.sender.api;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import ru.sergisa.sender.models.SenderResponse;


/**
 * Created by Sergey on 10/25/2017.
 */

public interface SenderAPI {

    @Headers("Authorization: Basic YWRtaW46aXNha292cw==")
    @GET("posts/{id}")
    Call<SenderResponse> getPost(
            @Path("id") int id
    );

    @Headers("Authorization: Basic YWRtaW46aXNha292cw==")
    @GET("posts/direct/{link}")
    Call<SenderResponse> getDirectPost(
            @Path("link") String link
    );

    @Headers("Authorization: Basic YWRtaW46aXNha292cw==")
    @GET("posts")
    Call<SenderResponse> getPosts();

    @Headers("Authorization: Basic YWRtaW46aXNha292cw==")
    @GET("link/create/{id}")
    Call<SenderResponse> getLink(
            @Path("id") String id
    );

    @Headers("Authorization: Basic YWRtaW46aXNha292cw==")
    @GET("link/create/{id}")
    Call<SenderResponse> getLink(
            @Path("id") int id
    );

    @Headers("Authorization: Basic YWRtaW46aXNha292cw==")
    @POST("posts/add")
    Call<SenderResponse> addPost(
            @Body SenderResponse.Post post
    );

    @GET("types")
    Call<SenderResponse> getTypes();
}
