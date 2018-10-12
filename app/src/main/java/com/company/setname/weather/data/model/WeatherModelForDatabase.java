package com.company.setname.weather.data.model;

public class WeatherModelForDatabase {

    //weather_main
    private Double main_min_temp;
    private Double main_max_temp;
    private Double main_tem;
    private Double main_pressure;
    private Integer main_humidity;

    //cloud
    private Integer cloud_percent;

    //Weather
    private Integer weather_id;
    private String weather_main;
    private String weather_description;
    private String weather_icon_id;

    //Wind
    private Double wind_speed;
    private Double wind_deg;

    public WeatherModelForDatabase(Double main_min_temp, Double main_max_temp,
                                   Double main_tem, Double main_pressure, Integer main_humidity,
                                   Integer cloud_percent, Integer weather_id, String weather_main,
                                   String weather_description, String weather_icon_id, Double wind_speed, Double wind_deg) {
        this.main_min_temp = main_min_temp;
        this.main_max_temp = main_max_temp;
        this.main_tem = main_tem;
        this.main_pressure = main_pressure;
        this.main_humidity = main_humidity;
        this.cloud_percent = cloud_percent;
        this.weather_id = weather_id;
        this.weather_main = weather_main;
        this.weather_description = weather_description;
        this.weather_icon_id = weather_icon_id;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
    }

    public WeatherModelForDatabase() {
    }

    public Double getMain_min_temp() {
        return main_min_temp;
    }

    public void setMain_min_temp(Double main_min_temp) {
        this.main_min_temp = main_min_temp;
    }

    public Double getMain_max_temp() {
        return main_max_temp;
    }

    public void setMain_max_temp(Double main_max_temp) {
        this.main_max_temp = main_max_temp;
    }

    public Double getMain_tem() {
        return main_tem;
    }

    public void setMain_tem(Double main_tem) {
        this.main_tem = main_tem;
    }

    public Double getMain_pressure() {
        return main_pressure;
    }

    public void setMain_pressure(Double main_pressure) {
        this.main_pressure = main_pressure;
    }

    public Integer getMain_humidity() {
        return main_humidity;
    }

    public void setMain_humidity(Integer main_humidity) {
        this.main_humidity = main_humidity;
    }

    public Integer getCloud_percent() {
        return cloud_percent;
    }

    public void setCloud_percent(Integer cloud_percent) {
        this.cloud_percent = cloud_percent;
    }

    public Integer getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(Integer weather_id) {
        this.weather_id = weather_id;
    }

    public String getWeather_main() {
        return weather_main;
    }

    public void setWeather_main(String weather_main) {
        this.weather_main = weather_main;
    }

    public String getWeather_description() {
        return weather_description;
    }

    public void setWeather_description(String weather_description) {
        this.weather_description = weather_description;
    }

    public String getWeather_icon_id() {
        return weather_icon_id;
    }

    public void setWeather_icon_id(String weather_icon_id) {
        this.weather_icon_id = weather_icon_id;
    }

    public Double getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Double wind_speed) {
        this.wind_speed = wind_speed;
    }

    public Double getWind_deg() {
        return wind_deg;
    }

    public void setWind_deg(Double wind_deg) {
        this.wind_deg = wind_deg;
    }

    @Override
    public String toString() {
        return "WeatherModelForDatabase{" +
                "main_min_temp=" + main_min_temp +
                ", main_max_temp=" + main_max_temp +
                ", main_tem=" + main_tem +
                ", main_pressure=" + main_pressure +
                ", main_humidity=" + main_humidity +
                ", cloud_percent=" + cloud_percent +
                ", weather_id=" + weather_id +
                ", weather_main='" + weather_main + '\'' +
                ", weather_description='" + weather_description + '\'' +
                ", weather_icon_id='" + weather_icon_id + '\'' +
                ", wind_speed=" + wind_speed +
                ", wind_deg=" + wind_deg +
                '}';
    }
}
