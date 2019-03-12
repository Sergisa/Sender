package ru.sergon.song.activity;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.sergon.song.R;
import ru.sergon.song.api.APIController;
import ru.sergon.song.models.SenderResponse;
import ru.sergon.song.models.SenderResponse.Type;
import ru.sergon.song.recycler.onFormSubmittedListener;
import ru.sergon.song.recycler.onFormValidatedListener;

public class PostForm implements View.OnClickListener {

    private onFormSubmittedListener onFormSubmittedListener;
    private onFormValidatedListener onFormValidatedListener;
    private EditText nameEdit,tagsEdit,codeEdit;
    private TextInputLayout nameEditLayout,tagsEditlayout,codeEditlayout;
    private View rootView;
    private Button submitButton;
    ProgressBar loader;
    LinearLayout container;
    Spinner typeSelector;
    private List<View> errorViews;
    private Context context;
    SpinnerCustomAdapter myTypeSelectorAdapter;

    Type selectedType;
    private boolean validated=false;

    PostForm(View root, Context context) {
        this.rootView = root;
        nameEdit = rootView.findViewById(R.id.titleEdit);
        tagsEdit = rootView.findViewById(R.id.tagsEdit);
        codeEdit = rootView.findViewById(R.id.codeEdit);

        loader = rootView.findViewById(R.id.progressBarForm);

        typeSelector = rootView.findViewById(R.id.typeSelector);

        submitButton = rootView.findViewById(R.id.saveButton);

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

        container = rootView.findViewById(R.id.parentFormLayout);

        APIController.getAPI().getTypes().enqueue(new Callback<SenderResponse>() {
            @Override
            public void onResponse(Call<SenderResponse> call, Response<SenderResponse> response) {
                myTypeSelectorAdapter = new SpinnerCustomAdapter(
                        context,
                        R.layout.row,
                        //android.R.layout.simple_spinner_item,
                        response.body().getTypes());
                typeSelector.setAdapter(myTypeSelectorAdapter);
                loader.setVisibility(View.INVISIBLE);
                container.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<SenderResponse> call, Throwable t) {
                Log.d("CodeEdit",t.getLocalizedMessage());
            }
        });

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

    @Override
    public String toString() {
        return super.toString();
    }

    public String getText() {
        return this.codeEdit.getText().toString();
    }
    public String getName(){
        return nameEdit.getText().toString();
    }

    public SenderResponse.Type getSelectedType(){
        return selectedType;
    }
}
