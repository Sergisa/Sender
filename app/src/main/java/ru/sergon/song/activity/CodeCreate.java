package ru.sergon.song.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sergon.song.R;
import ru.sergon.song.SenderApplication;
import ru.sergon.song.api.APIController;
import ru.sergon.song.api.SenderAPI;
import ru.sergon.song.models.SenderResponse;
import ru.sergon.song.recycler.onFormSubmittedListener;

public class CodeCreate extends AppCompatActivity implements View.OnClickListener {

    Button goToList;
    SenderAPI sender;
    PostForm form;
    private SenderResponse.Post post;
    SenderApplication app;
    SpinnerCustomAdapter myTypeSelectorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_create);



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sender = APIController.getAPI();
        app = (SenderApplication)getApplication();

        app.setPlace(this.getLocalClassName());
        form = new PostForm (findViewById(R.id.card), getApplicationContext());
        form.setLoading(true);
        APIController.getAPI().getTypes().enqueue(new Callback<SenderResponse>() {
            @Override
            public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {
                Log.d("CodeEdit",new Gson().toJson(response.body()));
                form.setSpinnerAdapter(new SpinnerCustomAdapter(getApplicationContext(),R.layout.row,response.body().getTypes()));
                //form.loadSpiner(response.body().getTypes());
                form.setLoading(false);
            }

            @Override
            public void onFailure(Call<SenderResponse> call, Throwable t) {
                Log.d("CodeEdit","error while loading spiner data"+t.getLocalizedMessage());
            }
        });

        form.setOnFormSubmittedListener(new onFormSubmittedListener() {
            @Override
            public void onFormSubmitted(View v) {
                Toast.makeText(getApplicationContext(),"Good Form",Toast.LENGTH_LONG).show();
                post = new SenderResponse.Post(
                        form.getName(),
                        form.getText(),
                        form.getTypeSelection()//specal
                );
                Log.d("CodeEdit","sending"+new Gson().toJson(post));

                sender.addPost(post).enqueue(new Callback<SenderResponse>() {

                    @Override
                    public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {
                        Log.d("CodeEdit","Mesasge"+response.raw().message());
                        Intent intent = new Intent(CodeCreate.this, FullActivity.class);

                        post.setLanguageName(form.getSelectedType().getLanguageName());
                        post.setLanguageCode(form.getSelectedType().getLanguageCode());
                        post.setId(Integer.valueOf( response.body().getMessage() ));
                        Gson gson = new Gson();
                        String artistString = null;
                        //object to json String
                        artistString = gson.toJson(post);


                        Log.d("CodeEdit","Mesasge"+response.raw().message());
                        Log.d("CodeEdit","Mesasge"+response.raw().toString());
                        Log.d("CodeEdit",artistString);
                        Log.d("CodeEdit","Mesasge"+response.body().getMessage());

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //передаём объект как json-строку
                        intent.putExtra("artist", artistString);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<SenderResponse> call, Throwable t) {
                        Log.d("CodeEdit",t.getMessage());
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onFormError(List<View> invalids) {

            }
        });


        goToList = findViewById(R.id.ListView);
        goToList.setOnClickListener(this);
        FloatingActionButton fab = findViewById(R.id.fab);

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(CodeCreate.this, MainActivity.class);
        startActivity(intent);
    }

}
