package ru.sergisa.sender.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;

import ru.sergisa.sender.R;


/**
 * Created by Sergey on 10/10/2017.
 */

public class ImageDialog extends DialogFragment{
    ImageView qrCodeImage;
    String link="(NONE)";

    static ImageDialog newInstance() {
        return new ImageDialog();
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("DialogDebug", "onCreateDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.image_dialog,null);
        qrCodeImage = (ImageView) view.findViewById(R.id.qrCodeImage);
        qrCodeImage.setImageBitmap(QRCode.from(link).withSize(450,450).bitmap());
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder
                // Add action buttons
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ImageDialog.this.getDialog().cancel();
                    }
                }).setView(view);
        return builder.create();


        /*
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.image_dialog, null);
        qrCodeImage = (ImageView) view.findViewById(R.id.qrCodeImage);
        qrCodeImage.setImageBitmap(QRCode.from(link).withSize(250,250).bitmap());
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setView(view);
        */
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*view = inflater.inflate(R.layout.image_dialog, container);
        qrCodeImage = (ImageView) view.findViewById(R.id.qrCodeImage);
        Log.d("DialogDebug", "onCreateView");
        return view;*/
        //return inflater.inflate(R.layout.image_dialog, container);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setLink(String link){
        this.link = link;
    }
}
