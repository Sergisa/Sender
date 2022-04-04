package ru.sergisa.sender.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.sergisa.sender.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class CodeCreateFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";


    public CodeCreateFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_code_create, container, false);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d("App_debug", "Code edit restored");
    }

}
