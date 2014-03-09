package com.fincatto.osm.views;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.fincatto.osm.R;

public class SelectPointTypeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_point_type);

        if (getIntent().getExtras() != null && getIntent().getExtras().get("lastKnownLocation") != null) {
            final Location lastKnownLocation = (Location) getIntent().getExtras().get("lastKnownLocation");
            Log.d(SelectPointTypeActivity.class.getSimpleName(), lastKnownLocation.toString());
        }
    }
}
