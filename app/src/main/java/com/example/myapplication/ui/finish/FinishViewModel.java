package com.example.myapplication.ui.finish;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FinishViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public FinishViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is finish fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
