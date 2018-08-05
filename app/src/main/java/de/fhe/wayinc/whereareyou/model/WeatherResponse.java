package de.fhe.wayinc.whereareyou.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class acts as a model for the weather API response.
 * It saves all the data the API returns.
 */

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

    /**
     * Get basic informations about the weather
     * @return A reference to the weather object holding info
     */
    public List<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }   // Internal parameter

    /**
     * Get the main info for the weather element
     * @return A reference to a Main object
     */
    public Main getMain() {
        return main;
    }   // Isn´t readable. Needs to be parsed.

    /**
     * Get the city code
     * @return The city code
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the current city
     * @return The name of the city
     */
    public String getName() {
        return name;
    }

    /**
     * Saves position info
     */
    private class Coord {
        private double lon;
        private double lat;
    }

    /**
     * Saves general weather info
     */
    public class Weather {
        private int id;
        private String main;
        private String description;
        private String icon;

        /**
         * Get the call ID
         * @return The ID
         */
        public int getId() {
            return id;
        }

        /**
         * Get a short weather description
         * @return The description
         */
        public String getDescription() {
            return description;
        }

        /**
         * Get the URL for the current weather icon
         * @return The URL
         */
        public String getIcon() {
            return icon;
        }
    }

    /**
     * Saves more basic info
     */
    public class Main {
        private double temp;
        private double pressure;
        private double humidity;
        private double temp_min;
        private double temp_max;
        private double sea_level;
        private double grnd_level;

        /**
         * Get the current temperature
         * @return The temperature in Kelvin
         */
        public double getTemp() {
            return temp;
        }
    }

    /**
     * Saves wind info
     */
    private class Wind {
        private double speed;
        private double deg;
    }

    /**
     * Saves cloud data
     */
    public class Clouds {
        private int all;

        /**
         * Get the amount of clouds in percent
         * @return The amount
         */
        public int getAll() { return all; }
    }

    /**
     * Saves rain info
     */
    private class Rain {
        @SerializedName("3h")
        private int rain3h;
    }

    /**
     * Saves snow info
     */
    private class Snow {
        @SerializedName("3h")
        private int snow3h;
    }

    /**
     * Saves basic info
     */
    public class Sys {
        private double message;
        private String country;
        private int sunrise;
        private int sunset;

        public double getMessage() {
            return message;
        }   // Returns an internal parameter
    }
}
