package com.company.setname.weather.adapter.model_of_items;

public class Model {

    long idInDatabase;

    String imageId;

    Double temperature;

    long timeMS;

    public Model(long idInDatabase, String imageId, Double temperature, long timeMS) {
        this.idInDatabase = idInDatabase;
        this.imageId = imageId;
        this.temperature = temperature;
        this.timeMS = timeMS;
    }

    public Model() {
    }

    public long getIdInDatabase() {
        return idInDatabase;
    }

    public void setIdInDatabase(long idInDatabase) {
        this.idInDatabase = idInDatabase;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public long getTimeMS() {
        return timeMS;
    }

    public void setTimeMS(long timeMS) {
        this.timeMS = timeMS;
    }
}
