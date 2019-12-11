package com.ucbits.ble_uart.pojo;

public class WeatherData {

    public Coord CoordObject = new Coord();
    public WeatherObj weather = new WeatherObj();
    public String base;
    public Main MainObject = new Main();
    public float visibility;
    public Wind WindObject = new Wind();
    public Clouds CloudsObject = new Clouds();
    public float dt;
    public Sys SysObject = new Sys();
    public float timezone;
    public float id;
    public String name;
    public float cod;
    public float longitude;
    public float latitude;
    public String country;
    public String city;

    // Getter Methods

    public Coord getCoord() {
        return CoordObject;
    }

    public String getBase() {
        return base;
    }

    public Main getMain() {
        return MainObject;
    }

    public float getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return WindObject;
    }

    public Clouds getClouds() {
        return CloudsObject;
    }

    public float getDt() {
        return dt;
    }

    public Sys getSys() {
        return SysObject;
    }

    public float getTimezone() {
        return timezone;
    }

    public float getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getCod() {
        return cod;
    }

    public WeatherObj getweather(){
        return  weather;
    }

    // Setter Methods

    public void setCoord(Coord coordObject) {
        this.CoordObject = coordObject;
    }

    public void setWeather(WeatherObj weather){
        this.weather = weather;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setMain(Main mainObject) {
        this.MainObject = mainObject;
    }

    public void setVisibility(float visibility) {
        this.visibility = visibility;
    }

    public void setWind(Wind windObject) {
        this.WindObject = windObject;
    }

    public void setClouds(Clouds cloudsObject) {
        this.CloudsObject = cloudsObject;
    }

    public void setDt(float dt) {
        this.dt = dt;
    }

    public void setSys(Sys sysObject) {
        this.SysObject = sysObject;
    }

    public void setTimezone(float timezone) {
        this.timezone = timezone;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCod(float cod) {
        this.cod = cod;
    }

    public class WeatherObj{
        private int id;
        private String main;
        private String description;

        public int getId(){
            return id;
        }

        public void setId(int id){
            this.id = id;
        }

        public String getMain(){
            return main;
        }

        public void setMain(String main){
            this.main = main;
        }

        public String getDescription(){
            return description;
        }

        public void setDescription(String description){
            this.description = description;
        }
    }

    public class Sys {
        private float type;
        private float id;
        private String country;
        private float sunrise;
        private float sunset;


        // Getter Methods

        public float getType() {
            return type;
        }

        public float getId() {
            return id;
        }

        public String getCountry() {
            return country;
        }

        public float getSunrise() {
            return sunrise;
        }

        public float getSunset() {
            return sunset;
        }

        // Setter Methods

        public void setType(float type) {
            this.type = type;
        }

        public void setId(float id) {
            this.id = id;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public void setSunrise(float sunrise) {
            this.sunrise = sunrise;
        }

        public void setSunset(float sunset) {
            this.sunset = sunset;
        }
    }

    public class Clouds {
        private float all;


        // Getter Methods

        public float getAll() {
            return all;
        }

        // Setter Methods

        public void setAll(float all) {
            this.all = all;
        }
    }

    public class Wind {
        private float speed;
        private float deg;


        // Getter Methods

        public float getSpeed() {
            return speed;
        }

        public float getDeg() {
            return deg;
        }

        // Setter Methods

        public void setSpeed(float speed) {
            this.speed = speed;
        }

        public void setDeg(float deg) {
            this.deg = deg;
        }
    }

    public class Main {
        private float temp;
        private float pressure;
        private float humidity;
        private float temp_min;
        private float temp_max;


        // Getter Methods

        public float getTemp() {
            return temp;
        }

        public float getPressure() {
            return pressure;
        }

        public float getHumidity() {
            return humidity;
        }

        public float getTemp_min() {
            return temp_min;
        }

        public float getTemp_max() {
            return temp_max;
        }

        // Setter Methods

        public void setTemp(float temp) {
            this.temp = temp;
        }

        public void setPressure(float pressure) {
            this.pressure = pressure;
        }

        public void setHumidity(float humidity) {
            this.humidity = humidity;
        }

        public void setTemp_min(float temp_min) {
            this.temp_min = temp_min;
        }

        public void setTemp_max(float temp_max) {
            this.temp_max = temp_max;
        }
    }

    public class Coord {
        private float lon;
        private float lat;


        // Getter Methods

        public float getLon() {
            return lon;
        }

        public float getLat() {
            return lat;
        }

        // Setter Methods

        public void setLon(float lon) {
            this.lon = lon;
        }

        public void setLat(float lat) {
            this.lat = lat;
        }
    }

    public float getLongitude() {
        return longitude;
    }
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public float getLatitude() {
        return latitude;
    }
    public void setLatitude(float latitude) { this.latitude = latitude; }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
}