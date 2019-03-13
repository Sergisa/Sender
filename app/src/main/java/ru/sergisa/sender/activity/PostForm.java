package ru.sergisa.sender.activity;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import ru.sergisa.sender.R;
import ru.sergisa.sender.models.SenderResponse;
import ru.sergisa.sender.models.SenderResponse.Type;
import ru.sergisa.sender.recycler.onFormSubmittedListener;
import ru.sergisa.sender.recycler.onFormValidatedListener;

public class PostForm implements View.OnClickListener {

    private onFormSubmittedListener onFormSubmittedListener;
    private onFormValidatedListener onFormValidatedListener;
    private EditText nameEdit,tagsEdit,codeEdit;
    private TextInputLayout nameEditLayout,tagsEditlayout,codeEditlayout;
    private ProgressBar loader;
    private LinearLayout container;
    private Spinner typeSelector;
    private List<View> errorViews;
    private Context context;
    private SpinnerCustomAdapter myTypeSelectorAdapter;

    private Type selectedType;
    private boolean validated=false;

    PostForm(View root, Context context) {
        nameEdit = root.findViewById(R.id.titleEdit);
        tagsEdit = root.findViewById(R.id.tagsEdit);
        codeEdit = root.findViewById(R.id.codeEdit);

        loader = root.findViewById(R.id.progressBarForm);

        typeSelector = root.findViewById(R.id.typeSelector);

        Button submitButton = root.findViewById(R.id.saveButton);

        nameEditLayout = (TextInputLayout) nameEdit.getParent().getParent();
        tagsEditlayout = (TextInputLayout) tagsEdit.getParent().getParent();
        codeEditlayout = (TextInputLayout) tagsEdit.getParent().getParent();



        typeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = myTypeSelectorAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        nameEditLayout.setErrorEnabled(true);
        submitButton.setOnClickListener(this);

        container = root.findViewById(R.id.parentFormLayout);
    }

    public void setSpinnerAdapter(SpinnerCustomAdapter adapter){
        myTypeSelectorAdapter = adapter;
        typeSelector.setAdapter(myTypeSelectorAdapter);
    }


    public void setLoading(boolean flag){
        if(flag){
            //hide form
            loader.setVisibility(View.VISIBLE);
            container.setVisibility(View.INVISIBLE);
        }else{
            //show form
            loader.setVisibility(View.INVISIBLE);
            container.setVisibility(View.VISIBLE);
        }
    }
    //itemClickListener.onItemClick();
    private void setViewError(View v, TextInputLayout layout){

        ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(0,0,android.R.drawable.ic_dialog_alert,0);
        if(layout != null){
            nameEditLayout.setError("Поле не должно быть пустым");
        }
    }

    private boolean validateForm(){
        boolean good = true;
        if(nameEdit.getText().toString().equals("")){
            nameEditLayout.setErrorEnabled(true);
            setViewError(nameEdit,nameEditLayout);

            good=false;
        }
        if(codeEdit.getText().toString().equals("")){
            codeEditlayout.setErrorEnabled(true);

            setViewError(codeEdit,codeEditlayout);
            good=false;

        }
        return good;
    }

    public void setOnFormValidatedListener (onFormValidatedListener onFormValidatedListener){
        this.onFormValidatedListener = onFormValidatedListener;
    }

    public onFormValidatedListener getOnFormValidatedListener(){
        return this.onFormValidatedListener;
    }

    void setOnFormSubmittedListener(onFormSubmittedListener onFormSubmittedListener) {
        this.onFormSubmittedListener = onFormSubmittedListener;
    }

    public onFormSubmittedListener getOnFormSubmittedListener(){
        return this.onFormSubmittedListener;
    }

    @Override
    public void onClick(View v) {
        validated = validateForm();
        if(validated) {
            onFormSubmittedListener.onFormSubmitted(v);
        }else{
            onFormSubmittedListener.onFormError(errorViews);
        }
    }

    SenderResponse.Type getTypeSelection() {

        return selectedType;
    }

    public boolean isValid(){
        return this.validated;
    }

    public String getText() {
        return this.codeEdit.getText().toString();
    }
    public String getName(){
        return nameEdit.getText().toString();
    }

    SenderResponse.Type getSelectedType(){
        return selectedType;
    }
}
