package com.example.gwftesting.measurements;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

@SuppressWarnings("unused")
public class Measurement implements Parcelable {

    private double lat;
    private double lng;
    private String mp_name;
    private String meter_id;
    private String comm_mod_type;
    private String comm_mod_serial;
    private String last_entry;
    private String volume;
    private int battery_lifetime;
    private HashMap<String, Boolean> state;

    public Measurement(double lat, double lng, String mp_name, String meter_id, String comm_mod_type, String comm_mod_serial, String last_entry, String volume, int battery_lifetime, HashMap<String, Boolean> state) {
        this.lat = lat;
        this.lng = lng;
        this.mp_name = mp_name;
        this.meter_id = meter_id;
        this.comm_mod_type = comm_mod_type;
        this.comm_mod_serial = comm_mod_serial;
        this.last_entry = last_entry;
        this.volume = volume;
        this.battery_lifetime = battery_lifetime;
        this.state = state;
    }


    public static final Creator<Measurement> CREATOR = new Creator<Measurement>() {
        @Override
        public Measurement createFromParcel(Parcel in) {
            return new Measurement(in);
        }

        @Override
        public Measurement[] newArray(int size) {
            return new Measurement[size];
        }
    };

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public String getMp_name() {
        return mp_name;
    }

    public void setMp_name(String mp_name) {
        this.mp_name = mp_name;
    }

    public String getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(String meter_id) {
        this.meter_id = meter_id;
    }

    public String getComm_mod_type() {
        return comm_mod_type;
    }

    public void setComm_mod_type(String comm_mod_type) {
        this.comm_mod_type = comm_mod_type;
    }

    public String getComm_mod_serial() {
        return comm_mod_serial;
    }

    public void setComm_mod_serial(String comm_mod_serial) {
        this.comm_mod_serial = comm_mod_serial;
    }

    public String getLast_entry() {
        return last_entry;
    }

    public void setLast_entry(String last_entry) {
        this.last_entry = last_entry;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public int getBattery_lifetime() {
        return battery_lifetime;
    }

    public void setBattery_lifetime(int battery_lifetime) {
        this.battery_lifetime = battery_lifetime;
    }

    public HashMap<String, Boolean> getState() {
        return state;
    }

    public void setState(HashMap<String, Boolean> state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
        dest.writeString(mp_name);
        dest.writeString(meter_id);
        dest.writeString(comm_mod_type);
        dest.writeString(comm_mod_serial);
        dest.writeString(last_entry);
        dest.writeString(volume);
        dest.writeInt(battery_lifetime);
    }

    protected Measurement(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
        mp_name = in.readString();
        meter_id = in.readString();
        comm_mod_type = in.readString();
        comm_mod_serial = in.readString();
        last_entry = in.readString();
        volume = in.readString();
        battery_lifetime = in.readInt();
    }
}
