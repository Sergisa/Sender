package ru.sergisa.sender.recycler;

import android.view.View;

import java.util.List;

public interface onFormSubmittedListener {
    void onFormSubmitted(View v);

    void onFormError(List<View> invalids);
}
