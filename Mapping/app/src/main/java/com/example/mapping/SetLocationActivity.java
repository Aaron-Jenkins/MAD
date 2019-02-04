package com.example.mapping;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import org.osmdroid.util.GeoPoint;

public class SetLocationActivity extends AppCompatActivity implements View.OnClickListener {




    public void onClick(View view) {
        EditText latEditText = (EditText) findViewById(R.id.et1);
        EditText lonEditText = (EditText) findViewById(R.id.et2);

        //get values and update location
        Double lon = parseLat(lonEditText);
        Double lat = parseLat(latEditText);
        mv.getController().setCenter(new GeoPoint(lat, lon));

    }


}
