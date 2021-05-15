package com.example.gwftesting.measurements;

import com.example.gwftesting.userUtilities.User;
import com.example.gwftesting.userUtilities.UserLoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ClientService {


    @POST("/auth/token/")
    Call<UserLoginResponse> login(@Body User user);

    @GET("/reports/measurements/total/")
    Call<MeasurementsTotal> getAllMeasurements(@Header("Authorization") String auth);

    @POST("/auth/token/refresh/")
    Call<UserLoginResponse> refresh(@Header("Authorization") String refreshToken);

    @GET("/reports/measurements/")
    Call<List<Measurement>> getAllMeasurementsValues(@Header("Authorization") String refreshToken);


}
