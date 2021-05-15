package com.example.gwftesting.measurements;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ApiViewModel extends ViewModel {

    private MutableLiveData<List<Measurement>> measurementMutableLiveData;

    public void init(Context context) {
        if (measurementMutableLiveData != null) {
            return;
        }
        ApiRepository apiRepository = ApiRepository.getInstance();
        measurementMutableLiveData = apiRepository.getMeasurements(context);
    }

    public LiveData<List<Measurement>> getMeasurements() {
        return measurementMutableLiveData;
    }


}
