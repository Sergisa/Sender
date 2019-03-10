package ru.sergon.song.activity;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

class CodeModel extends ViewModel {
    public MutableLiveData<String> code = new MutableLiveData<>();
    public MutableLiveData<String> lang = new MutableLiveData<>();

    public CodeModel(String code, String lang){
        this.code.setValue(code);
        this.lang.setValue(lang);
    }
}
