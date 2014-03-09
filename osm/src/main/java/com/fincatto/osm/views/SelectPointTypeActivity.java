package com.fincatto.osm.views;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fincatto.osm.R;
import com.fincatto.osm.classes.PointType;

public class SelectPointTypeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_point_type);

        //pego a ultima localizacao que foi passada para esta view
        final Location lastKnownLocation = (Location) getIntent().getExtras().get("lastKnownLocation");

        //pega o listview e adiciona um adapter com as opcoes validas e um listener para pegar a selecao
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, PointType.values()));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final PointType pointType = (PointType) listView.getItemAtPosition(position);
                final Intent intent = new Intent(getApplicationContext(), SelectPointTypeValueActivity.class);
                intent.putExtra("lastKnownLocation", lastKnownLocation);
                intent.putExtra("pointType", pointType);
                startActivity(intent);
            }
        });
    }
}
