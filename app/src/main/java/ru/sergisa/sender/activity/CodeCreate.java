package ru.sergisa.sender.activity;

import android.content.Intent;
import android.os.Bundle;
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
import ru.sergisa.sender.R;
import ru.sergisa.sender.SenderApplication;
import ru.sergisa.sender.api.SenderAPI;
import ru.sergisa.sender.models.SenderResponse;
import ru.sergisa.sender.recycler.onFormSubmittedListener;
import ru.sergisa.sender.utils.InternetConnectionListener;

public class CodeCreate extends AppCompatActivity implements View.OnClickListener, InternetConnectionListener {

    Button goToList;
    SenderAPI sender;
    Gson gson;
    PostForm form;
    private SenderResponse.Post post;
    Intent incomingIntent;
    String incomingAction;
    String incomingIntentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_create);
        gson = new Gson();

        goToList = findViewById(R.id.ListView);
        goToList.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sender = ((SenderApplication) getApplication()).getApiService();
        ((SenderApplication) getApplication()).setInternetConnectionListener(this);

        form = new PostForm(findViewById(R.id.card));
        form.setLoading();

        incomingIntent = getIntent();
        incomingAction = incomingIntent.getAction();
        incomingIntentType = incomingIntent.getType();

        sender.getTypes().enqueue(new Callback<SenderResponse>() {
            @Override
            public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {

                Log.d("CodeEdit", gson.toJson(response.body()));
                form.setSpinnerAdapter(new SpinnerCustomAdapter(getApplicationContext(), R.layout.row, response.body().getTypes()));
                form.setReady();
            }

            @Override
            public void onFailure(Call<SenderResponse> call, Throwable t) {
                Log.d("CodeEdit", "error while loading spinner data" + t.getLocalizedMessage());
            }
        });

        form.setOnFormSubmittedListener(new onFormSubmittedListener() {
            @Override
            public void onFormSubmitted(View v) {
                SenderResponse.Post postDataToSend = new SenderResponse.Post(
                        form.getName(),
                        form.getText(),
                        form.getTypeSelection()//special
                );
                Log.d("CodeEdit", "sending" + gson.toJson(post));

                sender.addPost(postDataToSend).enqueue(new Callback<SenderResponse>() {

                    @Override
                    public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {
                        //jumpToCodeButton.setVisibility(View.VISIBLE);
                        Log.d("CodeEdit", "Message" + response.raw().message());
                        Log.d("CodeEdit", "Post Response : " + gson.toJson(response.body().getResponse()[0]));

                        post = response.body().getResponse()[0];
                        String artistString = gson.toJson(post);

                        Log.d("CodeEdit", "Message" + response.raw().message());
                        Log.d("CodeEdit", "Message" + response.raw().toString());
                        Log.d("CodeEdit", artistString);
                        Log.d("CodeEdit", "Message" + response.body().getMessage());

                        //CopyLink(v, post.getLink());

                        jumpToCode();
                    }

                    @Override
                    public void onFailure(Call<SenderResponse> call, Throwable t) {
                        Log.d("CodeEdit", t.getMessage());
                        Toast.makeText(getApplicationContext(), "Отправка не удалась", Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
            }

            @Override
            public void onFormError(List<View> invalids) {
                //Toast.makeText(getApplicationContext(),"Good Form",Toast.LENGTH_LONG).show();

            }
        });

    }

    void handleIncomingIntent(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        String sharedTitle;
        if (intent.getStringExtra(Intent.EXTRA_TITLE) != null) {
            sharedTitle = intent.getStringExtra(Intent.EXTRA_TITLE);
        } else if (intent.getStringExtra(Intent.EXTRA_SUBJECT) == null) {
            sharedTitle = intent.getStringExtra(Intent.EXTRA_SUBJECT);
        } else {
            sharedTitle = "";
        }

        if (sharedText != null) {
            form.setNameEdit(sharedTitle);
            form.setCodeEdit(sharedText);

        }
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(CodeCreate.this, MainActivity.class));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        Log.d("App_debug", "SaveInstance");

    }

    @Override
    protected void onResume() {
        if (Intent.ACTION_SEND.equals(incomingAction) && incomingIntentType != null) {
            if (!"text/plain".equals(incomingIntentType)) {
                form.clear();
                post = null;
            } else {
                handleIncomingIntent(incomingIntent);
            }
        }
        super.onResume();

    }

    @Override
    protected void onPause() {
        ((SenderApplication) getApplication()).removeInternetConnectionListener();
        super.onPause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("App_debug", "RestoreInstance");
    }

    @Override
    public void onInternetUnavailable() {
        form.setError(getResources().getString(R.string.ethernet_error));
    }

    private void jumpToCode() {
        if (post != null) {
            Intent intent = new Intent(CodeCreate.this, FullActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //передаём объект как json-строку
            intent.putExtra("artist", gson.toJson(post));
            intent.putExtra("snack", true);
            startActivity(intent);
        }
    }
}
