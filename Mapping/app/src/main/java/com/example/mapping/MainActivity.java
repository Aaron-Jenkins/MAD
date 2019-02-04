package com.example.mapping;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    MapView mv;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        // This line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_main);
        /*
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        EditText et1 = (EditText) findViewById(R.id.et1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        EditText et2 = (EditText) findViewById(R.id.et2);
        Button b = (Button)findViewById(R.id.btn1);
        b.setOnClickListener(this);
        */
        mv = (MapView)findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(16);
        mv.getController().setCenter(new GeoPoint( 51.05,-0.72));

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

    public void onClick(View view) {
        /*
        EditText latEditText = (EditText) findViewById(R.id.et1);
        EditText lonEditText = (EditText) findViewById(R.id.et2);

        //get values and update location
        Double lon = parseLat(lonEditText);
        Double lat = parseLat(latEditText);
        mv.getController().setCenter(new GeoPoint(lat, lon));
        */
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == R.id.choosemap)
        {
            Intent intent = new Intent(this,MapChooseActivity.class);
            startActivityForResult(intent,0);
            return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent intent)
    {

        if(requestCode==0)
        {

            if (resultCode==RESULT_OK)
            {
                Bundle extras=intent.getExtras();
                boolean hikebikemap = extras.getBoolean("com.example.hikebikemap");
                if(hikebikemap==true)
                {
                    mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                }
                else
                {
                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        }
    }
}
