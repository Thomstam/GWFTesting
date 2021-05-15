package com.example.gwftesting.utilities;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;

import com.example.gwftesting.R;
import com.example.gwftesting.databinding.ActivityMeasurementPointBinding;
import com.example.gwftesting.measurements.CustomInfoWindowAdapter;
import com.example.gwftesting.measurements.Measurement;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class MeasurementPoint extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 8f;

    private Measurement measurementForPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        measurementForPreview = bundle.getParcelable("measurement");


        com.example.gwftesting.databinding.ActivityMeasurementPointBinding binding = ActivityMeasurementPointBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NotNull GoogleMap googleMap) {
        mMap = googleMap;

        setMapSettings();

        setBackButton();


        LatLng previewPlace = new LatLng(measurementForPreview.getLng(), measurementForPreview.getLat());
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MeasurementPoint.this, measurementForPreview));
        Objects.requireNonNull(mMap.addMarker(getMarker(measurementForPreview.getLng(), measurementForPreview.getLat()))).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(previewPlace));

        moveCamera(previewPlace);
    }

    private MarkerOptions getMarker(double longitude, double latitude) {
        MarkerOptions options = new MarkerOptions().position(new LatLng(longitude, latitude)).snippet(null);
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        return options;
    }

    private void moveCamera(LatLng latLng) {

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, DEFAULT_ZOOM));
    }

    private void setMapSettings() {
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);
    }

    private void setBackButton() {
        ImageButton backButton = findViewById(R.id.measurement_point_back_button);
        backButton.setOnClickListener(v -> finish());
    }
}