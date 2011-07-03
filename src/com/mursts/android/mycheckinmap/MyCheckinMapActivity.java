package com.mursts.android.mycheckinmap;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

/**
 * @see http://developer.android.com/resources/tutorials/views/hello-mapview.html
 *
 */
public class MyCheckinMapActivity extends MapActivity
    implements DownloadChekinInfoCallBack {

    private static final GeoPoint DEFAULT_POINT = new GeoPoint(
            new Double(36.5626 * 1E6).intValue(),
            new Double(136.362305 * 1E6).intValue());

    MapView mMap;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mMap = (MapView)findViewById(R.id.map);

        final MapView map = mMap;
        map.setBuiltInZoomControls(true);
        MapController mapCon = map.getController();
        mapCon.setZoom(6);
        mapCon.setCenter(DEFAULT_POINT);

        final MyLocationOverlay overlay =
            new MyLocationOverlay(getApplicationContext(), map);
        overlay.onProviderEnabled(LocationManager.GPS_PROVIDER);
        overlay.enableMyLocation();
        overlay.runOnFirstFix(new Runnable() {
            @Override
            public void run() {
                map.getController().animateTo(overlay.getMyLocation());
            }
        });
        map.getOverlays().add(overlay);

        new DownloadCheckinInfoTask(this, this).execute();
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    @Override
    public void onDownloadFinish(ArrayList<CheckinInfo> checkinList) {
        List<Overlay> mapOverlays = mMap.getOverlays();
        Drawable drawable = getResources().getDrawable(android.R.drawable.star_on);
        CheckinMapItemizedOverlay itemOverlay = new CheckinMapItemizedOverlay(drawable, getApplicationContext());

        for (CheckinInfo checkin : checkinList) {
            itemOverlay.addOverlay(
                    new OverlayItem(
                        checkin.getPoint(), checkin.getTitle(), checkin.getDate()));
        }
        mapOverlays.add(itemOverlay);
    }

    @Override
    public void onDownloadFailed() {
        Toast.makeText(
            getApplicationContext(), R.string.download_failed, Toast.LENGTH_SHORT).show();
    }
}