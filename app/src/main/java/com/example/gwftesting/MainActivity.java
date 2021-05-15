package com.example.gwftesting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.example.gwftesting.drawerFragments.SettingsFragment;
import com.example.gwftesting.measurements.ApiClient;
import com.example.gwftesting.measurements.ClientService;
import com.example.gwftesting.measurements.MeasurementsTotal;
import com.example.gwftesting.measurements.TokenSaver;
import com.example.gwftesting.userUtilities.UserLoginResponse;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final ClientService retrofit = ApiClient.createService(ClientService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getMeasurements();

        setRecyclerViewFragment();

        setMenu();

        settingsFragmentInit();
    }

    private void getMeasurements() {
        final MeasurementsTotal[] measurementsTotal = {new MeasurementsTotal()};
        String token = TokenSaver.getSharedPrefAccess(this);
        Call<MeasurementsTotal> measurementsTotalCall = retrofit.getAllMeasurements("Bearer " + token);
        measurementsTotalCall.enqueue(new Callback<MeasurementsTotal>() {
            @Override
            public void onResponse(@NotNull Call<MeasurementsTotal> call, @NotNull Response<MeasurementsTotal> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    measurementsTotal[0] = response.body();
                    setViews(measurementsTotal[0]);
                } else {
                    if (refreshToken()) {
                        getMeasurements();
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Connection Time Out", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MeasurementsTotal> call, @NotNull Throwable t) {

            }
        });
    }

    private boolean refreshToken() {
        String token = TokenSaver.getSharedPrefRefresh(this);
        final boolean[] isRefreshed = {false};
        Call<UserLoginResponse> userLoginResponseCall = retrofit.refresh("Bearer " + token);
        userLoginResponseCall.enqueue(new Callback<UserLoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<UserLoginResponse> call, @NotNull Response<UserLoginResponse> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    TokenSaver.setSharedPrefAccess(MainActivity.this, response.body().getAccess());
                    isRefreshed[0] = true;
                }
            }

            @Override
            public void onFailure(@NotNull Call<UserLoginResponse> call, @NotNull Throwable t) {

            }
        });
        return isRefreshed[0];
    }


    public void setViews(MeasurementsTotal measurementsTotal) {
        TextView meters = findViewById(R.id.totalMeters);
        TextView volume = findViewById(R.id.totalUsageNumber);
        TextView readouts = findViewById(R.id.totalReadoutsNumber);
        TextView errors = findViewById(R.id.totalErrorsNumber);
        meters.setText(measurementsTotal.getMeter());
        volume.setText(measurementsTotal.getVolume());
        readouts.setText(measurementsTotal.getReadouts());
        errors.setText(measurementsTotal.getErrors());

    }

    private void setRecyclerViewFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.activity_main_recycler_view_container_fragment, new RecyclerViewFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setMenu() {
        Toolbar toolbar = findViewById(R.id.mainToolBarVol2);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawerMainActivity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void settingsFragmentInit() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.settingsFragment, new SettingsFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}