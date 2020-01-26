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

    private ProgressBar loader;
    private LinearLayout container;
    private Spinner typeSelector;
    private List<View> errorViews;
    private Context context;
    private SpinnerCustomAdapter myTypeSelectorAdapter;
    private TextView errorText;
    private Type selectedType;
    private boolean validated=false;

    PostForm(View root, Context context) {
        nameEdit = root.findViewById(R.id.titleEdit);
        tagsEdit = root.findViewById(R.id.tagsEdit);
        codeEdit = root.findViewById(R.id.codeEdit);

        loader = root.findViewById(R.id.progressBarForm);

        typeSelector = root.findViewById(R.id.typeSelector);

        Button submitButton = root.findViewById(R.id.saveButton);

        errorText = root.findViewById(R.id.ErrorMessage);

        typeSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedType = myTypeSelectorAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        submitButton.setOnClickListener(this);

        container = root.findViewById(R.id.parentFormLayout);
    }

    void setSpinnerAdapter(SpinnerCustomAdapter adapter){
        myTypeSelectorAdapter = adapter;
        typeSelector.setAdapter(myTypeSelectorAdapter);
    }


    void setLoading(){
        setState(FormState.LOADING);
    }
    void setError(String error_string) {
        errorText.setText(error_string);
        setState(FormState.ERROR);
    }
    void setReady(){
        setState(FormState.READY);
    }
    //itemClickListener.onItemClick();
    private void setViewError(View v){
        TextInputLayout parentLayout = (TextInputLayout)v.getParent().getParent();
        ((TextView)v).setCompoundDrawablesWithIntrinsicBounds(0,0,android.R.drawable.ic_dialog_alert,0);
        if(parentLayout != null){
            parentLayout.setErrorEnabled(true);
            parentLayout.setError("Поле не должно быть пустым");
        }
    }

    private boolean validateForm(){
        boolean good = true;
        /*if(nameEdit.getText().toString().equals("")){
            setViewError(nameEdit);
            good=false;
        }*/
        if(codeEdit.getText().toString().equals("")){
            setViewError(codeEdit);
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


    private void setState(FormState state){
        if(state == FormState.ERROR){
            container.setVisibility(View.INVISIBLE);
            errorText.setVisibility(View.VISIBLE);
            loader.setVisibility(View.INVISIBLE);
        }else if(state == FormState.LOADING){
            errorText.setVisibility(View.INVISIBLE);
            loader.setVisibility(View.VISIBLE);
            container.setVisibility(View.INVISIBLE);
        }else if(state == FormState.READY){
            errorText.setVisibility(View.INVISIBLE);
            loader.setVisibility(View.INVISIBLE);
            container.setVisibility(View.VISIBLE);
        }
    }

    public void clear() {
        typeSelector.setSelected(false);
        nameEdit.setText("");
        codeEdit.setText("");
        tagsEdit.setText("");
    }

    public void setNameEdit(String nameEdit) {
        this.nameEdit.setText(nameEdit);
    }

    public void setCodeEdit(String codeEdit) {
        this.codeEdit.setText(codeEdit);
    }

    public void setPost(SenderResponse.Post post){
        setNameEdit(post.getTitle());
        setCodeEdit(post.getText());
    }

    public enum FormState {
        ERROR,
        LOADING,
        READY;

        /*private final String stateName;

        FormState(String stateName) {
            this.stateName = stateName;
        }

        public String getNameState() {
            return stateName;
        }*/

    }

}
