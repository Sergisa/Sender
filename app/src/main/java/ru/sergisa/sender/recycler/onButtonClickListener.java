package ru.sergisa.sender.recycler;

import android.view.View;

import ru.sergisa.sender.models.SenderResponse.Post;

/**
 * Created by Sergey on 10/10/2017.
 */

public interface onButtonClickListener {
    void onButtonClick(View v, Post post, int i);
}
