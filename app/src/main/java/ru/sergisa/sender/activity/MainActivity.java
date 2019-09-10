package ru.sergisa.sender.activity;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sergisa.sender.R;
import ru.sergisa.sender.SenderApplication;
import ru.sergisa.sender.api.SenderAPI;
import ru.sergisa.sender.models.SenderResponse;
import ru.sergisa.sender.models.SenderResponse.Post;
import ru.sergisa.sender.recycler.RVAdapter;
import ru.sergisa.sender.recycler.onButtonClickListener;
import ru.sergisa.sender.recycler.onItemClickListener;
import ru.sergisa.sender.utils.InternetConnectionListener;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, Callback<SenderResponse> {

    public Context context;
    //PostRequest request;

    RecyclerView postsListView;
    public Post[] posts;
    Activity main = this;
    ProgressBar progressBar;
    private int openedPost=0;
    FloatingActionButton upFab;
    RVAdapter adapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageDialog qrcodeDialog;
    SenderAPI sender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        qrcodeDialog = ImageDialog.newInstance();
        sender = ((SenderApplication)getApplication()).getApiService();
        // ((SenderApplication)getApplication()).setInternetConnectionListener(this);
//      отображаем индикатор загрузки
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        mSwipeRefreshLayout = findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark
                );
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        context = getApplicationContext();
        //вызываем парсер
        upFab = findViewById(R.id.fab);


        loadData();
        //new ParseTask().execute();
    }

    private void loadData(){
        sender.getPosts().enqueue(this);
    }

    @Override
    public void onRefresh() {
        loadData();
    }



    @Override
    public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {
        Log.d("Retrofit_debug", "success2");
        posts = response.body().getResponse();


        postsListView=findViewById(R.id.artistList);

        customLayoutManager mLayoutManager = new customLayoutManager(main);
        postsListView.setLayoutManager(mLayoutManager);
        postsListView.setHasFixedSize(true);
        //initializeAdapter();
        adapter = new RVAdapter(main, context, posts);
        postsListView.setAdapter(adapter);

        Log.d("App_debug", Integer.toString( postsListView.getAdapter().getItemCount()));
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        upFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postsListView.smoothScrollToPosition(0);
                //artistsView.scrollToPosition(0);
            }
        });
        //обработчик нажатий на элемент
        mSwipeRefreshLayout.setRefreshing(false);
        adapter.setOnButtonClickListener(new onButtonClickListener() {
            @Override
            public void onButtonClick(final View v, final Post post, final int i) {
                switch (v.getId()){
                    case R.id.linkButton:
                        if(post.hasLink()) {
                            CopyLink(v, post.getLink());
                        }else{

                            sender.getLink(post.getId()).enqueue(new Callback<SenderResponse>() {
                                @Override
                                public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {

                                    postsListView.findViewHolderForAdapterPosition(i)
                                            .itemView
                                            .findViewById(R.id.qrBtn)
                                         .setVisibility(View.VISIBLE);

                                    post.setLink(response.body().getResponse()[0].getLink());
                                    ((Button)v).setText(response.body().getResponse()[0].getLink());
                                    ((Button)v).setTextColor(context.getResources().getColorStateList(R.color.violet_button));
                                    ((Button)v).setBackgroundResource(R.drawable.grey_outline_button);
                                }

                                @Override
                                public void onFailure(Call<SenderResponse> call, Throwable t) {

                                }
                            });
                            //TODO here must be request to get link for id
                        }
                        break;
                    case R.id.qrBtn:
                        qrcodeDialog.setLink(getString(R.string.url)+post.getLink());
                        qrcodeDialog.show(getSupportFragmentManager(),"qr");
                        break;
                }
            }
        });
        adapter.setOnItemClickListener(new onItemClickListener() {
            @Override
            public void onItemClick(Post post, int i) {
                Log.d("App_debug",post.getId()+" "+Integer.toString(i));

                Intent intent = new Intent(MainActivity.this, FullActivity.class);

                Gson gson = new Gson();
                String artistString = null;
                //object to json String
                artistString = gson.toJson(posts[i]);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                openedPost=i;
                //передаём объект как json-строку
                intent.putExtra("artist", artistString);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onFailure(Call<SenderResponse> call, Throwable t) {

    }


    public void CopyLink(View v, final String link){
        ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(" ", getString(R.string.url) + link);

        clipboard.setPrimaryClip(clip);
        Snackbar.make(v, getApplicationContext().getResources().getText(R.string.snackbar_text), Snackbar.LENGTH_LONG)
            .setAction(getApplicationContext().getResources().getText(R.string.snackbar_button_text), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent browserIntent = null;
                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url) + link.toString()));
                    startActivity(browserIntent);
                }
            }).show();
    }


    @Override
    protected void onResume() {
        Log.d("App_lifecycle", this.getClass().getSimpleName()+" resume");
        if(openedPost!=0){
            this.postsListView.scrollToPosition(openedPost);
        }
        super.onResume();
    }

    //вывод сообщения при нажатии на индикатор загрузки
    public void onProgressClick(View view){
        Toast.makeText(context,"Подождите, идёт загрузка!", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        ((SenderApplication)getApplication()).removeInternetConnectionListener();
        super.onPause();
    }
}


