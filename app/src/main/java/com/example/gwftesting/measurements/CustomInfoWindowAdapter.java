package com.example.gwftesting.measurements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.gwftesting.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.jetbrains.annotations.NotNull;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private final Measurement measurement;

    @SuppressLint("InflateParams")
    public CustomInfoWindowAdapter(Context context, Measurement measurementsTotal) {
        this.measurement = measurementsTotal;
        mWindow = LayoutInflater.from(context).inflate(R.layout.measurement_point_activity_custom_marker_info, null);
    }

    private void renderWindow(View view) {
        TextView measurementPoint = view.findViewById(R.id.measurementPointActivityMeasurementPoint);
        TextView meter = view.findViewById(R.id.measurementPointActivityMeter);
        TextView module = view.findViewById(R.id.measurementPointActivityModule);
        TextView volume = view.findViewById(R.id.measurementPointActivityVolume);
        measurementPoint.setText(measurement.getMp_name());
        meter.setText(measurement.getMeter_id());
        module.setText(measurement.getComm_mod_serial());
        volume.setText(measurement.getVolume());
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View getInfoWindow(@NonNull @NotNull Marker marker) {
        renderWindow(mWindow);
        return mWindow;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View getInfoContents(@NonNull @NotNull Marker marker) {
        renderWindow(mWindow);
        return mWindow;
    }
}
