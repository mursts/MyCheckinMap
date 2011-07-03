package com.mursts.android.mycheckinmap;

import com.google.android.maps.GeoPoint;

public class CheckinInfo {
    private String title;
    private String link;
    private String date;
    private GeoPoint point;

    public CheckinInfo(String title, String link, String date, String point) {
        this.title = title;
        this.link = link;
        this.date = date;
        this.point = convertGeoPoint(point);
    }

    private GeoPoint convertGeoPoint(String point) {
        String[] points = point.split(" ");
        Double lat = new Double(points[0]);
        Double lon = new Double(points[1]);
        return new GeoPoint(
                new Double(lat * 1E6).intValue(),
                new Double(lon * 1E6).intValue());
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDate() {
        return date;
    }

    public GeoPoint getPoint() {
        return point;
    }
}
