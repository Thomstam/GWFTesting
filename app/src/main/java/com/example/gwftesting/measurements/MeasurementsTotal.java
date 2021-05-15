package com.example.gwftesting.measurements;

@SuppressWarnings("unused")
public class MeasurementsTotal {

    private String meters;
    private String volume;
    private String errors;
    private String readouts;


    public String getMeter() {
        return meters;
    }

    public void setMeter(String meter) {
        this.meters = meter;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getReadouts() {
        return readouts;
    }

    public void setReadouts(String readouts) {
        this.readouts = readouts;
    }
}
