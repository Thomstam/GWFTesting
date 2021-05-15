package com.example.gwftesting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwftesting.measurements.ApiViewModel;
import com.example.gwftesting.utilities.MeasurementPoint;
import com.example.gwftesting.utilities.MeasurementsRecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecyclerViewFragment extends Fragment {

    RecyclerView recyclerView;
    MeasurementsRecyclerView measurementsRecyclerView;

    public RecyclerViewFragment() {
        super(R.layout.recycler_view_fragment);
    }


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.recycler_view_fragment, container, false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateData();

        setRecyclerView();

        setRecyclerViewOnClick();

        setRefreshValues();

        setSearchBar();
    }

    private void populateData() {
        ApiViewModel apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        apiViewModel.init(requireContext());
        apiViewModel.getMeasurements().observe(requireActivity(), measurementList -> {
            if (measurementList != null) {
                measurementsRecyclerView.setMeasurementList(measurementList);
            } else {
                Intent intent = new Intent(requireActivity(), LoginActivity.class);
                startActivity(intent);
                Toast.makeText(requireContext(), "Connection Time Out", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRecyclerView() {
        recyclerView = requireView().findViewById(R.id.recycler_view_fragment_measurement_list);
        measurementsRecyclerView = new MeasurementsRecyclerView();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(measurementsRecyclerView);
    }

    private void setRecyclerViewOnClick() {
        measurementsRecyclerView.setOnItemClickListener(measurement -> {
            if (measurement.getLng() != 0.0 && measurement.getLat() != 0.0) {
                Intent mapsIntent = new Intent(requireActivity(), MeasurementPoint.class);
                mapsIntent.putExtra("measurement", measurement);
                startActivity(mapsIntent);
            } else {
                Toast.makeText(getActivity(), "No Place Information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRefreshValues() {
        ImageButton refresh = requireView().findViewById(R.id.recycler_view_fragment_refresh_values);
        refresh.setOnClickListener(v -> populateData());
    }

    private void setSearchBar() {
        SearchView searchView = requireView().findViewById(R.id.recycler_view_fragment_searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                measurementsRecyclerView.getFilter().filter(newText);
                return false;
            }
        });
    }


}
