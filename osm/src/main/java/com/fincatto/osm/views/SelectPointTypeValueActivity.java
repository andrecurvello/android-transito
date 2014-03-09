package com.fincatto.osm.views;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.fincatto.osm.R;
import com.fincatto.osm.classes.PointType;

public class SelectPointTypeValueActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_select_point_type_value);

        //pego a ultima localizacao e o tipo de ponto que foi passada para esta view
        final Location lastKnownLocation = (Location) getIntent().getExtras().get("lastKnownLocation");
        final PointType pointType = (PointType) getIntent().getExtras().get("pointType");

        //pega o gridview e adiciona um adapter com as opcoes validas e um listener para pegar a selecao
        final GridView gridView = (GridView) findViewById(R.id.grid);
        gridView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pointType.getSpeedOptions()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //salva o ponto
                final Integer velocidade = (Integer) gridView.getItemAtPosition(position);
                Log.d(SelectPointTypeValueActivity.class.getSimpleName(), String.format("Tipo: %s, Velocidade: %s, Local: %s", pointType, velocidade, lastKnownLocation.toString()));

                //volta para a home
                final Intent main = new Intent(getApplicationContext(), MainActivity.class);
                main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
            }
        });
    }
}
