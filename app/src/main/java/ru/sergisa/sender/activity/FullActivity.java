package ru.sergisa.sender.activity;

/**
 * Created by Sergisa on 25.04.2016.
 */

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sergisa.sender.HighlightJsView;
import ru.sergisa.sender.R;
import ru.sergisa.sender.SenderApplication;
import ru.sergisa.sender.api.SenderAPI;
import ru.sergisa.sender.models.Language;
import ru.sergisa.sender.models.SenderResponse;
import ru.sergisa.sender.models.SenderResponse.Post;
import ru.sergisa.sender.models.Theme;


public class FullActivity extends AppCompatActivity implements Callback<SenderResponse> {
    TextView title, type, link;
    HighlightJsView codeView;
    Button linkCreate, qrCaller;
    private Post post;
    String artistString;
    private ImageDialog qrcodeDialog;
    ProgressBar progressBarFull;
    //SenderAPI client;
    SenderAPI sender;
    SenderApplication app;

    private ShareActionProvider shareActionProvider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qrcodeDialog = new ImageDialog();
        setContentView(R.layout.activity_full);

        app= (SenderApplication)getApplicationContext();

        sender = ((SenderApplication)getApplication()).getApiService();
        title = findViewById(R.id.title);
        codeView = findViewById(R.id.codeView);
        progressBarFull = findViewById(R.id.progressBarFull);
        type = findViewById(R.id.type);
        linkCreate = findViewById(R.id.linkCreate);
        qrCaller = findViewById(R.id.qrShow);

        progressBarFull.setVisibility(ProgressBar.VISIBLE);
        Intent i = getIntent();
        Uri data = i.getData();



        if(data!=null){
            // entering by external link
            title.setText(data.getQueryParameter("direct_link"));

            /*Map<String,String> obj = new HashMap<>();
            obj.put("filter","link");
            obj.put("val",data.getQueryParameter("direct_link"));
            Call<SenderResponse> call = client.getPost(obj);*/
            Call<SenderResponse> call = sender.getDirectPost(data.getQueryParameter("direct_link"));
            call.enqueue(this);

        }else {
            //if entering from app

            //JSON String from app
            artistString = getIntent().getStringExtra("artist");
            Log.d("Debug_full_activity","Incoming:"+artistString);
            //JSON Object string to class convert

            Gson gson2 = new Gson();
            post = gson2.fromJson(artistString,Post.class);

            updateViews();
        }


    }

    //кнопка "Домой" - как кнопка "Назад" чтобы не скролить заного вниз листа
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.share:
                if (post.hasLink()) {


                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Ссылка на код");
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, Uri.parse(getString(R.string.url) + post.getLink()).toString());
                    startActivity(Intent.createChooser(sharingIntent, "Куда отправить ссылку?"));
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_code_create, menu);
        if(!post.hasLink()){
            menu.findItem(R.id.share).setVisible(false);
        }

        return true;
        //return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {
        Log.d("Debug_full_activity", "success2");
        post = response.body().getResponse()[0];
        linkCreate.setText(response.body().getResponse()[0].getLink());
        Log.d("Debug_full_activity", response.body().getResponse()[0].getText());
        updateViews();
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP){
            codeView.setInitialScale(((int) codeView.getScale())+1);
            return true;
        }else if(keyCode==KeyEvent.KEYCODE_VOLUME_DOWN) {
            codeView.setInitialScale(((int) codeView.getScale())-1);
            return true;
        }else{

            return super.dispatchKeyEvent(event);
        }
        //return super.onKeyDown(keyCode, event);
    }*/

    private void updateViews(){
        Log.d("Debug_full_activity","Start updating views");
        progressBarFull.setVisibility(ProgressBar.VISIBLE);

        type.setText(post.getLanguageName());
        progressBarFull.setVisibility(ProgressBar.GONE);
        if(post.hasLink()) {
            linkCreate.setText(post.getLink());
            linkCreate.setVisibility(View.VISIBLE);
        }else {
            qrCaller.setVisibility(View.INVISIBLE);
        }
        if (post.hasTitle()) {
            title.setText(post.getTitle());
        }else{
            SpannableString span = new SpannableString("Нет заголовка");
            span.setSpan(new ForegroundColorSpan(Color.GRAY), 0, "Нет заголовка".length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            title.setText(span);
        }
        codeView.setSource(post.getText());
        codeView.getSettings().setLoadWithOverviewMode(true);
        codeView.setZoomSupportEnabled(true);
        codeView.getSettings().setBuiltInZoomControls(true);
        codeView.getSettings().setDisplayZoomControls(true);

        codeView.setShowLineNumbers(true);
        type.setText(post.getLanguageName());
        codeView.setTheme(Theme.ARDUINO_LIGHT);
        codeView.setHighlightLanguage(Language.getLang(post.getLanguageCode()));
        codeView.refresh();
        progressBarFull.setVisibility(ProgressBar.GONE);

        try {
            codeView.setOnContentChangedListener(() -> {
                Log.d("Debug_full_activity", "onContentChanged");
                progressBarFull.setVisibility(ProgressBar.GONE);
            });

            setTitle(post.getTitle());
            qrCaller.setOnClickListener(view -> {
                qrcodeDialog.setLink(getString(R.string.url)+post.getLink());
                qrcodeDialog.show(getSupportFragmentManager(), "qr");
            });
            linkCreate.setOnClickListener(view -> {
                if(post.hasLink()){
                    ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("", getString(R.string.url) + post.getLink());

                    clipboard.setPrimaryClip(clip);
                    Snackbar.make(view, getApplicationContext().getResources().getText(R.string.snackbar_text), Snackbar.LENGTH_LONG)
                            .setAction(getApplicationContext().getResources().getText(R.string.snackbar_button_text), v -> {
                                Intent browserIntent = null;
                                if (post.hasLink()) {
                                    browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url)+ post.getLink().toString()));
                                }
                                startActivity(browserIntent);
                            }).show();
                }else{
                    Log.d("Debug_full_activity", "POST "+new Gson().toJson(post));
                    Log.d("Debug_full_activity", "sending id: "+post.getId());
                    sender.getLink(post.getId()).enqueue(new Callback<SenderResponse>() {
                        @Override
                        public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {
                            Log.d("Debug_full_activity", response.raw().toString());
                            post.setLink(response.body().getResponse()[0].getLink());
                            linkCreate.setText(post.getLink());
                            qrCaller.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onFailure(Call<SenderResponse> call, Throwable t) {

                        }
                    });
                }
            });

        } catch (NullPointerException e){
            e.printStackTrace();
            Log.d("Debug_full_activity", "Null pointer triggered");

        }
    }


    @Override
    protected void onPause() {
        app.removeInternetConnectionListener();
        super.onPause();
    }

    @Override
    public void onFailure(Call<SenderResponse> call, Throwable t) {
        Log.d("Debug_full_activity", "failure2");
        Log.d("Debug_full_activity",t.getMessage() +t.getLocalizedMessage());
    }
}
