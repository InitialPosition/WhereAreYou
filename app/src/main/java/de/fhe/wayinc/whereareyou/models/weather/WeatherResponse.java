package de.fhe.wayinc.whereareyou.models.weather;

import java.util.List;

public class WeatherResponse {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private Main main;
    private Wind wind;
    private Clouds clouds;
    private Rain rain;
    private Snow snow;
    private int dt;
    private Sys sys;
    private int id;
    private String name;
    private int cod;

    public Coord getCoord() {
        return coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }   // Internal parameter

    public Main getMain() {
        return main;
    }   // Isn´t readable. Needs to be parsed.

    public Wind getWind() {
        return wind;
    }   // Isn´t readable. Needs to be parsed.

    public Clouds getClouds() {
        return clouds;
    }   // Isn´t readable. Needs to be parsed.

    public Rain getRain() {
        return rain;
    }   // Isn´t readable. Needs to be parsed.

    public Snow getSnow() {
        return snow;
    }   // Isn´t readable. Needs to be parsed.

    public int getDt() {
        return dt;
    }   // Time of data calculation in unix timestamp

    public Sys getSys() {
        return sys;
    }   // Isn´t readable. Needs to be parsed.

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    } // Returns the name of the city

    public int getCod() {
        return cod;
    }   // Internal parameter. Not the same as Coord.

    public class Coord {
        private double lon;
        private double lat;

        public double getLon() {
            return lon;
        }

        public double getLat() {
            return lat;
        }
    }

    public class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        public int getId() {
            return id;
        }

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }
    }

    public class Main {
        private double temp;
        private double pressure;
        private double humidity;
        private double temp_min;
        private double temp_max;
        private double sea_level;
        private double grnd_level;

        public double getTemp() {
            return temp;
        }   // Returns the temperature in kelvin

        public double getPressure() {
            return pressure;
        }   // Returns the atmospheric pressure in hPa

        public double getHumidity() {
            return humidity;
        }   // Returns the humidity in percent

        public double getTemp_min() {
            return temp_min;
        }   // Returns min temperature in the city

        public double getTemp_max() {
            return temp_max;
        }   // Returns max temperature in the city

        public double getSea_level() {
            return sea_level;
        }   // Returns atmospheric pressure on the sea level in hPa

        public double getGrnd_level() {
            return grnd_level;
        }   // Returns atmospheric pressure on the ground level in hPa
    }

    public class Wind {
        private double speed;
        private int deg;

        public double getSpeed() {
            return speed;
        }   // Returns wind speed in m/s

        public int getDeg() {
            return deg;
        }   //Returns the direction of the wind
    }

    public class Clouds {
        private int all;

        public int getAll() { return all; }   // Returns ammount of clouds in percent
    }

    public class Rain {
        private int rain3h;

        public int getRain3h() { return rain3h; }   // Returns rain volume for the last 3 hours
    }

    public class Snow {
        private int snow3h;

        public int getSnow3h() { return snow3h; }   // Returns snow volume for the last 3 hours
    }

    public class Sys {
        private double message;
        private String country;
        private int sunrise;
        private int sunset;

        public double getMessage() {
            return message;
        }   // Returns an internal parameter

        public String getCountry() {
            return country;
        }   // Returns Countrycode

        public int getSunrise() {
            return sunrise;
        }   // Returns Sunrise in unix timestamp

        public int getSunset() {
            return sunset;
        }   // Returns Sunset in unix timestamp
    }
}
