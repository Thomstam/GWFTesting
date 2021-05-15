package com.example.gwftesting.measurements;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.gwftesting.userUtilities.UserLoginResponse;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private static ApiRepository apiRepository;


    public static ApiRepository getInstance() {
        if (apiRepository == null) {
            apiRepository = new ApiRepository();
        }
        return apiRepository;
    }

    private final ClientService retrofitApi;

    public ApiRepository() {
        retrofitApi = ApiClient.createService(ClientService.class);
    }

    public MutableLiveData<List<Measurement>> getMeasurements(Context context) {
        String token = TokenSaver.getSharedPrefAccess(context);
        MutableLiveData<List<Measurement>> measurementList = new MutableLiveData<>();
        retrofitApi.getAllMeasurementsValues("Bearer " + token).enqueue(new Callback<List<Measurement>>() {
            @Override
            public void onResponse(@NotNull Call<List<Measurement>> call, @NotNull Response<List<Measurement>> response) {
                if (response.isSuccessful()) {
                    measurementList.setValue(response.body());
                } else {
                    if (refreshToken(context)) {
                        getMeasurements(context);
                    } else {
                        measurementList.setValue(null);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Measurement>> call, @NotNull Throwable t) {

            }
        });
        return measurementList;
    }


    private boolean refreshToken(Context context) {
        String token = TokenSaver.getSharedPrefRefresh(context);
        final boolean[] isRefreshed = {false};
        Call<UserLoginResponse> userLoginResponseCall = retrofitApi.refresh("Bearer " + token);
        userLoginResponseCall.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserLoginResponse> call, @NotNull Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    TokenSaver.setSharedPrefAccess(context, response.body().getAccess());
                    isRefreshed[0] = true;
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserLoginResponse> call, @NotNull Throwable t) {

            }
        });
        return isRefreshed[0];
    }

}
