package ru.sergon.song.recycler;

import android.view.View;

import ru.sergon.song.models.SenderResponse.Post;

/**
 * Created by Sergey on 10/10/2017.
 */

public interface onButtonClickListener {
    public void onButtonClick(View v, Post post, int i);
}
