package com.example.gwftesting.utilities;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwftesting.R;
import com.example.gwftesting.measurements.Measurement;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MeasurementsRecyclerView extends RecyclerView.Adapter<MeasurementsRecyclerView.MeasurementViewHolder> implements Filterable {

    private List<Measurement> measurementList;
    private List<Measurement> measurementListAll;
    private onItemClickListener listener;

    public void setMeasurementList(List<Measurement> measurementList) {
        this.measurementList = measurementList;
        if (measurementListAll == null){
            measurementListAll = new ArrayList<>(measurementList);
        }
        notifyData();
    }

    public void notifyData() {
        notifyDataSetChanged();
    }

    public MeasurementsRecyclerView(){

    }


    @NonNull
    @NotNull
    @Override
    public MeasurementViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_measurements, parent, false);
        return new MeasurementsRecyclerView.MeasurementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@android.support.annotation.NonNull MeasurementsRecyclerView.MeasurementViewHolder holder, int position) {

        holder.meterID.setText(measurementList.get(position).getMeter_id());
        if (measurementList.get(position).getLat() == 0.0 || measurementList.get(position).getLng() == 0.0) {
            holder.meterID.setGravity(Gravity.CENTER_VERTICAL);
            holder.mapIcon.setVisibility(View.GONE);
            holder.mapName.setVisibility(View.GONE);
        } else {
            holder.mapName.setText(measurementList.get(position).getMp_name());
        }
        holder.commModType.setText(measurementList.get(position).getComm_mod_type());
        holder.commModSerial.setText(measurementList.get(position).getComm_mod_serial());
    }

    @Override
    public int getItemCount() {
        return measurementList == null ? 0 : measurementList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<Measurement> filteredList = new ArrayList<>();
            if (constraint.toString().isEmpty() || constraint.length() == 0){
                filteredList.addAll(measurementListAll);
            }else {
                for (Measurement measurement : measurementListAll) {
                    if (measurement.getMeter_id().toLowerCase().contains(constraint.toString().toLowerCase())){
                        filteredList.add(measurement);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            measurementList.clear();
            if (results.values != null){
                measurementList.addAll((Collection<? extends Measurement>) results.values);
            }
            notifyDataSetChanged();
        }
    };




    public class MeasurementViewHolder extends RecyclerView.ViewHolder {

        final TextView meterID;
        final TextView mapName;
        final TextView commModType;
        final TextView commModSerial;
        final ImageView mapIcon;

        public MeasurementViewHolder(@android.support.annotation.NonNull View itemView) {
            super(itemView);
            meterID = itemView.findViewById(R.id.recycler_view_meter_id);
            mapName = itemView.findViewById(R.id.recycler_view_mp_name);
            commModType = itemView.findViewById(R.id.recycler_view_comm_mod_type);
            commModSerial = itemView.findViewById(R.id.recycler_view_comm_mod_serial);
            mapIcon = itemView.findViewById(R.id.recycler_view_map_icon);
            itemView.setOnClickListener(v -> listener.onItemClick(measurementList.get(getAbsoluteAdapterPosition())));
        }
    }
    public interface onItemClickListener {
        void onItemClick(Measurement measurement);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
