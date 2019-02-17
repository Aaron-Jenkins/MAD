package com.example.mapping;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MapView mv;
    Double latitude = 51.05;
    Double longitude = -0.72;
    Integer zoom = 16;
    private String isRecording = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            isRecording = savedInstanceState.getString("isRecording");
        }
        // This line sets the user agent, a requirement to download OSM maps
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        setContentView(R.layout.activity_main);

        //center map
        mv = findViewById(R.id.map1);
        mv.setBuiltInZoomControls(true);
        mv.getController().setZoom(zoom);
        mv.getController().setCenter(new GeoPoint(latitude, longitude));


        /*
        TextView tv1 = (TextView) findViewById(R.id.tv1);
        EditText et1 = (EditText) findViewById(R.id.et1);
        TextView tv2 = (TextView) findViewById(R.id.tv2);
        EditText et2 = (EditText) findViewById(R.id.et2);
        Button b = (Button)findViewById(R.id.btn1);
        b.setOnClickListener(this);
        */
    }
    public void onResume()
    {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        double lat = Double.parseDouble ( prefs.getString("lat", "50.9") );
        double lon = Double.parseDouble ( prefs.getString("lon", "-1.4") );
        boolean autodownload = prefs.getBoolean("autodownload", true);
        mv.getController().setCenter(new GeoPoint(lat,lon));

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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.choosemap) {
            Intent intent = new Intent(this, MapChooseActivity.class);
            startActivityForResult(intent, 0);
            return true;
        } else if (item.getItemId() == R.id.SetLocation) {
            Intent requestIntent = new Intent(this, SetLocationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putDouble("com.example.mapping.latitude", latitude);
            bundle.putDouble("com.example.mapping.longitude", longitude);
            bundle.putInt("com.example.mapping.zoom", zoom);
            requestIntent.putExtras(bundle);
            startActivityForResult(requestIntent, 1);
            return true;
        } else if (item.getItemId() == R.id.Prefs) {
            Intent intent = new Intent (this, MyPreferenceActivity.class);
            startActivityForResult(intent, 2);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy()  {
        super.onDestroy();
        // save the chosen map
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString ("isRecording", isRecording);
        editor.commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("isRecording", isRecording);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {


        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {
                Bundle extras = intent.getExtras();
                boolean hikebikemap = extras.getBoolean("com.example.hikebikemap");
                if (hikebikemap == true) {
                    mv.setTileSource(TileSourceFactory.HIKEBIKEMAP);
                } else {

                    mv.setTileSource(TileSourceFactory.MAPNIK);
                }
            }
        } else if (requestCode == 1) {
            // result from  choose location activity
            if (resultCode == RESULT_OK) {
                Bundle extras = intent.getExtras();
                double latitude = extras.getDouble("com.example.mapping.latitude");
                double longitude = extras.getDouble("com.example.mapping.longitude");
                mv.getController().setCenter(new GeoPoint(latitude, longitude));
            }
        }
    }


}
