package ru.sergisa.sender.recycler;

import android.view.View;

import java.util.List;

public interface onFormSubmittedListener {
    public void onFormSubmitted(View v);
    public void onFormError(List<View> invalids);
}
