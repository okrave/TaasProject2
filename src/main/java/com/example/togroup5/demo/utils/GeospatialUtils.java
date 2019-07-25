package com.example.togroup5.demo.utils;

public class GeospatialUtils {

    /**
     * latitude and longitude in degrees.
     * Returns distance in Kilometers
     */
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 == lat2) && (lon1 == lon2))
            return 0;
        else {
            lat1 = Math.toRadians(lat1);
            lon1 = Math.toRadians(lon1);
            lat2 = Math.toRadians(lat2);
            lon2 = Math.toRadians(lon2);
            return Math.toDegrees(Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2)))
                    * 60 * 1.1515 * 1.609344; // the last costant is used to convert from miles to km
        }
    }
}
