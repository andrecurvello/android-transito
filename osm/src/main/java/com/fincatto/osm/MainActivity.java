package com.fincatto.osm;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        //configura o mapa
        final MapView map = (MapView) findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        //map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setMultiTouchControls(true);
        map.getController().setZoom(17);


        final String locationProvider = LocationManager.GPS_PROVIDER;
        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        final Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
        map.getController().setCenter(new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude()));

        //atualiza a localizacao a cada segundo ou um metro
        locationManager.requestLocationUpdates(locationProvider, 1000, 1, new MapLocationListener(map));
    }
}

class MapLocationListener implements LocationListener {

    private final MapView map;

    public MapLocationListener(final MapView map) {
        this.map = map;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.w(MapLocationListener.class.getSimpleName(), "onLocationChanged");
        map.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.w(MapLocationListener.class.getSimpleName(), "onStatusChanged: " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.w(MapLocationListener.class.getSimpleName(), "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.w(MapLocationListener.class.getSimpleName(), "onProviderDisabled");
    }
}