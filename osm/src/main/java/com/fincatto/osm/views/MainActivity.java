package com.fincatto.osm.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.fincatto.osm.R;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;
import org.osmdroid.views.util.constants.MapViewConstants;

public class MainActivity extends Activity implements MapViewConstants {

    private static final int ATUALIZACAO_MT = 10; //10 metros
    private static final int ATUALIZACAO_MS = 1000; //1 segundo
    private static final String LOCATION_PROVIDER = LocationManager.GPS_PROVIDER;

    private MapView map;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private MyLocationNewOverlay myLocationOverlay;
    private CompassOverlay compassOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);

        //configura o mapa
        this.map = (MapView) findViewById(R.id.mapview);
        this.map.setTileSource(TileSourceFactory.MAPNIK); //TileSourceFactory.MAPQUESTOSM
        this.map.setMultiTouchControls(true);
        this.map.getController().setZoom(17);

        //configura o gerente de localizacao
        this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        //centraliza o mapa na ultima localizacao conhecida
        final Location lastKnownLocation = locationManager.getLastKnownLocation(LOCATION_PROVIDER);
        if (lastKnownLocation != null) {
            final GeoPoint geoPointCenter = new GeoPoint(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
            this.map.getController().setCenter(geoPointCenter);
        }

        //adiciona a bussola no mapa
        this.compassOverlay = new CompassOverlay(this, this.map);
        this.compassOverlay.enableCompass();
        this.map.getOverlays().add(compassOverlay);

        //adiciona o usuario no mapa
        this.myLocationOverlay = new MyLocationNewOverlay(this, this.map);
        myLocationOverlay.setDrawAccuracyEnabled(true);
        myLocationOverlay.enableFollowLocation();
        myLocationOverlay.enableMyLocation();
        this.map.getOverlays().add(myLocationOverlay);

        //atualiza a localizacao a cada segundo ou um metro
        this.locationListener = new MapLocationListener(map);
        locationManager.requestLocationUpdates(LOCATION_PROVIDER, ATUALIZACAO_MS, ATUALIZACAO_MT, locationListener);

//        //notification test
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack(MainActivity.class);
//        stackBuilder.addNextIntent(new Intent(this, SelectPointTypeActivity.class));
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setContentTitle("My notification").setContentText("Hello World!");
//        builder.setContentIntent(stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT));
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(666, builder.build());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //lista os pontos ja inseridos
//        for(Point point : new DBPoint(this.getApplicationContext()).findAll()) {
//            Log.d("point", point.toString());
//        }

        //vai para selecao do tipo do ponto
        final Intent intent = new Intent(getApplicationContext(), SelectPointTypeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("lastKnownLocation", locationManager.getLastKnownLocation(LOCATION_PROVIDER));
        this.startActivity(intent);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        //adiciona um ponto no mapa
        //this.map.getOverlays().add(new MapOverlay(this, this.map.getMapCenter(), Color.GREEN));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.locationManager.requestLocationUpdates(LOCATION_PROVIDER, ATUALIZACAO_MS, ATUALIZACAO_MT, this.locationListener);
        this.myLocationOverlay.enableMyLocation();
        this.myLocationOverlay.enableFollowLocation();
        this.compassOverlay.enableCompass();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.locationManager.removeUpdates(this.locationListener);
        this.myLocationOverlay.disableMyLocation();
        this.myLocationOverlay.disableFollowLocation();
        this.compassOverlay.disableCompass();
    }
}

class MapLocationListener implements LocationListener {

    private final MapView map;

    public MapLocationListener(final MapView map) {
        this.map = map;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(MapLocationListener.class.getSimpleName(), "onLocationChanged: " + location.toString());
        this.map.getController().setCenter(new GeoPoint(location.getLatitude(), location.getLongitude()));
        //this.map.setMapOrientation(location.getBearing());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}

//class MapOverlay extends Overlay {
//
//    private final IGeoPoint geoPoint;
//    private final int color;
//
//    public MapOverlay(final Context ctx, final IGeoPoint geoPoint, final int color) {
//        super(ctx);
//        this.geoPoint = geoPoint;
//        this.color = color;
//    }
//
//    @Override
//    protected void draw(Canvas canvas, MapView mapView, boolean shadow) {
//        if (!shadow) {
//            //cria um marca
//            final Paint paint = new Paint();
//            paint.setStyle(Paint.Style.FILL);
//            paint.setColor(this.color);
//            paint.setAntiAlias(true);
//
//            //adiciona a marca no meio do mapa
//            final Rect viewportRect = new Rect();
//            viewportRect.set(mapView.getProjection().getScreenRect());
//            canvas.drawCircle(viewportRect.centerX(), viewportRect.centerY(), 10, paint);
//        }
//    }
//}