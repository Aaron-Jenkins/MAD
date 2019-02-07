package com.example.mapping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.osmdroid.util.GeoPoint;

public class SetLocationActivity extends AppCompatActivity implements View.OnClickListener {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_set_location);

        Bundle extras = this.getIntent().getExtras();
        Double latitude = extras.getDouble("com.example.mapping.latitude");
        Double longitude = extras.getDouble("com.example.mapping.longitude");
        Integer zoom = extras.getInt("com.example.mapping.zoom");


        // initialise default lat/long
        EditText et1 = (EditText) findViewById(R.id.et1);
        et1.setText(latitude.toString());
        EditText et2 = (EditText) findViewById(R.id.et2);
        et2.setText(longitude.toString());

        Button b = (Button)findViewById(R.id.btn1);
        b.setOnClickListener(this);

    }

    public Double parseLong (EditText geoEditText) {
        String input = geoEditText.getText().toString();
        try {
            //parse string to double
            Double longitude = Double.parseDouble(input);
            //validate data
            if (longitude > 90 || longitude < -90) {
                geoEditText.setText(""); //reset text if data invalid
                geoEditText.setHint("invalid longitude: " + input);
                String message = "invalid longitude";
                new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(message).show();
                return null;
            }
            return longitude;
        } catch (Exception e) {
            geoEditText.setText("");
            geoEditText.setHint("invalid longitude: " + input);
            String message = "invalid longitude: " + input;
            new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(message).show();
            return null;
        }
    }

    public Double parseLat (EditText geoEditText) {
        String input = geoEditText.getText().toString();
        try {
            //parse string to double
            Double latitude = Double.parseDouble(input);
            //validate data
            if (latitude > 90 || latitude < -90) {
                geoEditText.setText(""); //reset text if data invalid
                geoEditText.setHint("invalid latitude: " + input);
                String message = "invalid latitude";
                new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(message).show();
                return null;
            }
            return latitude;
        } catch (Exception e) {
            geoEditText.setText("");
            geoEditText.setHint("invalid latitude: " + input);
            String message = "invalid latitude: " + input;
            new AlertDialog.Builder(this).setPositiveButton("OK", null).setMessage(message).show();
            return null;
        }
    }

    public void onClick(View view) {

        // create intent
        Intent intent = new Intent();
        Bundle bundle=new Bundle();

        Double latitude = 51.05;
        Double longitude = -0.72;
        Integer zoom = 16;

        EditText latEditText = (EditText) findViewById(R.id.et1);
        EditText lonEditText = (EditText) findViewById(R.id.et2);



        //get values and update location
        Double lon = parseLat(lonEditText);
        Double lat = parseLat(latEditText);

        if(lon!=null && lat!=null) {
            latitude = lat;
            longitude = lon;
            bundle.putDouble("com.example.mapping.latitude",latitude);
            bundle.putDouble("com.example.mapping.longitude",longitude);
            bundle.putDouble("com.example.mapping.latitude",latitude);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }

    }


}
